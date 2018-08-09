package com.devplant.wallapp.proprties;

import com.devplant.wallapp.model.Account;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

//lombok
@Data
// Spring
@ConfigurationProperties("devplant.init")
public class InitialAccountProperties {

    private List<Account> accounts = new ArrayList<>();

}
