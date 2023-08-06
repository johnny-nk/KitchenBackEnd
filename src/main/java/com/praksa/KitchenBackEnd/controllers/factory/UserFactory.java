package com.praksa.KitchenBackEnd.controllers.factory;

import com.praksa.KitchenBackEnd.models.dto.CookRegisterDTO;
import com.praksa.KitchenBackEnd.models.dto.RegularUserRegisterDTO;
import com.praksa.KitchenBackEnd.models.dto.UserRegisterDTO;
import com.praksa.KitchenBackEnd.models.entities.Administrator;
import com.praksa.KitchenBackEnd.models.entities.Cook;
import com.praksa.KitchenBackEnd.models.entities.EUserRole;
import com.praksa.KitchenBackEnd.models.entities.RegularUser;
import com.praksa.KitchenBackEnd.models.entities.User;
import com.praksa.KitchenBackEnd.util.Encryption;



public class UserFactory {
	
	public static User createUser(UserRegisterDTO userDTO) {
		
		if (userDTO.getRole() == EUserRole.ADMINISTRATOR) {
			
			Administrator admin = new Administrator();
			
			admin.setUsername(userDTO.getUsername());
			admin.setPassword(Encryption.getPassEncoded(userDTO.getPassword()));
			admin.setRole(userDTO.getRole());
			
			return admin;
			
		} else if (userDTO.getRole() == EUserRole.REGULARUSER) {
			
			RegularUserRegisterDTO regUserDTO = (RegularUserRegisterDTO) userDTO;
			RegularUser regUser = new RegularUser();
			
			regUser.setFirstName(regUserDTO.getFirstName());
			regUser.setLastName(regUserDTO.getLastName());
			regUser.setUsername(regUserDTO.getUsername());
			regUser.setPassword(Encryption.getPassEncoded(regUserDTO.getPassword()));
			regUser.setEmail(regUserDTO.getEmail());
			regUser.setRole(regUserDTO.getRole());
			
			return regUser;
			
		} else {
			
			CookRegisterDTO cookDTO = (CookRegisterDTO) userDTO;
			Cook cook = new Cook();
			
			cook.setFirstName(cookDTO.getFirstName());
			cook.setLastName(cookDTO.getLastName());
			cook.setUsername(cookDTO.getUsername());
			cook.setPassword(Encryption.getPassEncoded(cookDTO.getPassword()));
			cook.setEmail(cookDTO.getEmail());
			cook.setRole(cookDTO.getRole());
			cook.setAboutMe(cookDTO.getAboutMe());
			
			return cook;
		}
	}
}
