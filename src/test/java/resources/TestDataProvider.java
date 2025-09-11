package resources;

import java.util.Random;
import java.util.UUID;

import serialization.SaveHeaderInfo;

public class TestDataProvider {

	 public static SaveHeaderInfo createRandomContract() {
	        Random rand = new Random();
	        SaveHeaderInfo contract = new SaveHeaderInfo();

	        contract.setContractid(rand.nextInt(1000));
	        contract.setContracttitle("Title_" + UUID.randomUUID().toString());
	        contract.setContractstageid(1);
	        contract.setContractdescription("Description_" + UUID.randomUUID().toString());
	        contract.setContractkeywords("Keyword_" + rand.nextInt(100) + ",Keyword_" + rand.nextInt(100));
	        contract.setContractexecutedat("Location_" + rand.nextInt(100));
	        contract.setAnnualcontractvalue(rand.nextInt(10000));
	        contract.setCurrencyId(53);
	        contract.setTypeid(13);
	        contract.setCategoryid(18);
	        contract.setClassificationid(28);
	        contract.setProcessid(31);
	        contract.setUserID(3);
	        contract.setTenantid(null); // or rand.nextInt(10)

	        return contract;

	 }
} 
