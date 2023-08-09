package com.praksa.KitchenBackEnd.util.startup;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import com.praksa.KitchenBackEnd.models.dto.AdminRegisterDTO;
import com.praksa.KitchenBackEnd.models.dto.CookRegisterDTO;
import com.praksa.KitchenBackEnd.models.dto.RegularUserRegisterDTO;
import com.praksa.KitchenBackEnd.models.entities.EUserRole;
import com.praksa.KitchenBackEnd.models.entities.Ingredient;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;
import com.praksa.KitchenBackEnd.models.entities.LimitingIngredient;
import com.praksa.KitchenBackEnd.repositories.IngredientRepository;
import com.praksa.KitchenBackEnd.repositories.LimitingFactorRepository;
import com.praksa.KitchenBackEnd.repositories.LimitingIngredientRepository;
import com.praksa.KitchenBackEnd.services.UserService;

@Component
public class StartupDatasetWrite implements ApplicationListener<ApplicationReadyEvent> {

	private static final Logger logger = Logger.getLogger(StartupDatasetWrite.class.getName());
	@Value("${spring.jpa.hibernate.ddl-auto}")
	String ddlStatus;

	@Autowired
	private LimitingFactorRepository lfRepo;
	@Autowired
	private LimitingIngredientRepository limitingIngredientRepo;
	@Autowired
	private IngredientRepository ingredientRepository;
	@Autowired
	private UserService userService; 

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		logger.info("DDL Status: " + ddlStatus);
		if (ddlStatus.equals("create")) {
			logger.info("Populating database");
//			InputStream resource;
//			CSVReader reader;
//
			try {

				// Citanje i pisanje ogranicavajucih faktora
				InputStream resourceLimitingFactors = new ClassPathResource("data/factors.csv").getInputStream();
				CSVReader readerLimitingFactors = new CSVReaderBuilder(new InputStreamReader(resourceLimitingFactors))
						.build();
				@SuppressWarnings({ "rawtypes", "unchecked" })
				Set<LimitingFactor> limits = new HashSet<>(
						new CsvToBeanBuilder(readerLimitingFactors).withType(LimitingFactor.class).build().parse());
				logger.info("Writing Limiting Factors...");
				lfRepo.saveAll(limits);
				readerLimitingFactors.close();
				logger.info("Done writing Limiting Factors");

				// Citanje i pisanje sastojaka
				InputStream resourceIngredients = new ClassPathResource("data/ingredients.csv").getInputStream();
				CSVReader readerIngredients = new CSVReaderBuilder(new InputStreamReader(resourceIngredients)).build();
				@SuppressWarnings({ "rawtypes", "unchecked" })
				List<IngredientStartupDTO> ingredients = new CsvToBeanBuilder(readerIngredients)
						.withType(IngredientStartupDTO.class).build().parse();
				logger.info("Writing Ingredients...");
				for (IngredientStartupDTO ingredient : ingredients) {
					Ingredient in = new Ingredient();
					in.setName(ingredient.getName());
					in.setUnit(ingredient.getUnit());
					in.setCalories(ingredient.getCalories());
					in.setCarbs(ingredient.getCarbs());
					in.setFats(ingredient.getFats());
					in.setSaturatedFats(ingredient.getSaturatedFats());
					in.setSugars(ingredient.getSugars());
					in.setProteins(ingredient.getProteins());
					in = ingredientRepository.save(in);
				}
				logger.info("Done writing Ingredients");
				readerIngredients.close();

				// Povezivanje sastojka i ogranicenja

				List<Ingredient> ingredientsDB = (List<Ingredient>) ingredientRepository.findAll();
				Map<String, IngredientStartupDTO> ingredientsStartupDTOMap = new HashMap<>();
				for (IngredientStartupDTO in : ingredients) {
					ingredientsStartupDTOMap.put(in.getName(), in);
				}
				logger.info("Writing Ingredient -> LimitingFactor connections...");
				for (Ingredient in : ingredientsDB) {
					List<String> templateLimitingFactors = (ingredientsStartupDTOMap.get(in.getName()))
							.getLimitingFactors().stream().map(e -> e.trim()).toList();
					List<LimitingIngredient> limitsForIn = templateLimitingFactors.stream().map(e -> {
						LimitingIngredient li = new LimitingIngredient();
						LimitingFactor lf = lfRepo.findByName(e);
						li.setLimitingFactor(lf);
						li.setIngredients(in);

						return li;
					}).filter(e -> e.getLimitingFactor() != null).toList();
					in.setLimitingFactor(limitsForIn);

					limitingIngredientRepo.saveAll(in.getLimitingFactors());
				}

				logger.info("Done writing connections");
				logger.info("Writing users...");
				
				// Dodavanje korisnika u bazu
				InputStream resourceUsers = new ClassPathResource("data/users.csv").getInputStream();
				CSVReader readerUsers = new CSVReaderBuilder(new InputStreamReader(resourceUsers))
						.build();
				@SuppressWarnings({ "rawtypes", "unchecked" })
				List<UserStartupDTO> users = new ArrayList<>(
						new CsvToBeanBuilder(readerUsers).withType(UserStartupDTO.class).build().parse());
				
//				for(UserStartupDTO u: users) {
//					logger.info(u.toString());
//				}
				
				for(UserStartupDTO u: users) {
					switch(u.getRole()) {
					case "ADMINISTRATOR": {
						logger.info(u.getRole());
						AdminRegisterDTO admin = new AdminRegisterDTO(
								u.getUsername(),
								u.getPassword(),
								EUserRole.valueOf(u.getRole())
								);
						userService.addAdmin(admin);
						break;
					}
					case "REGULARUSER": {
						logger.info(u.getRole());
						RegularUserRegisterDTO user = new RegularUserRegisterDTO(
								u.getUsername(),
								u.getPassword(),
								EUserRole.valueOf(u.getRole())
								);
						user.setEmail(u.getEmail());
						user.setFirstName(u.getFirstName());
						user.setLastName(u.getLastName());
						userService.addUser(user);
						break;
					}
					case "COOK": {
						logger.info(u.getRole());
						CookRegisterDTO cook = new CookRegisterDTO(
								u.getUsername(),
								u.getPassword(),
								EUserRole.valueOf(u.getRole()));
						cook.setFirstName(u.getFirstName());
						cook.setLastName(u.getLastName());
						cook.setEmail(u.getEmail());
						cook.setAboutMe(u.getAboutMe());
						userService.addCook(cook);
						break;
					}
					default: {
						break;
					}
					}
				}
				
				readerUsers.close();
				
				// Pisanje recepata
				

				
				logger.info("Done writing.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else
			logger.info("Skipping DB write");
	}

}

//try {
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	List<Ingredient> ingredients = new CsvToBeanBuilder(new FileReader(file))
//			.withType(Ingredient.class).build().parse();
////	repo.saveAll(ingredients);
//	for(Ingredient ingredient : ingredients) {
//		System.out.println(ingredient);
//		repo.save(ingredient);
//	}
//	
//} catch (IllegalStateException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//} catch (FileNotFoundException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//}
