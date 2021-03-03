package magicbeans.runnable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MoleculeDataMap {

	private static Map<String, String> moleculeDataMap = initMoleculeDataMap();
	
	private static Map<String, String> initMoleculeDataMap() {
		moleculeDataMap = new LinkedHashMap<String, String>();

		moleculeDataMap.put("Water", "water");
		moleculeDataMap.put("Ethane", "ethane");
		moleculeDataMap.put("Hyaluronic Acid", "HyaluronicAcid");
		moleculeDataMap.put("Benzene", "benzene");
		moleculeDataMap.put("Buckminsterfullerine", "buckminsterfullerine");
		moleculeDataMap.put("Cyclohexane", "cyclohexane");
		moleculeDataMap.put("DNA", "dna");
		
		return(moleculeDataMap);
	}
	
	public static Map<String, String> getMoleculeDataMap(){
		return(moleculeDataMap);
	}
	
	public static String[] getKeySetArray() {
		Set<String> keySet = moleculeDataMap.keySet();
		String[] keySetArray = new String[keySet.size()];
		int i = 0;
		for(String key : keySet) {
			keySetArray[i] = key;
			i++;
		}
		return(keySetArray);
	}
}
