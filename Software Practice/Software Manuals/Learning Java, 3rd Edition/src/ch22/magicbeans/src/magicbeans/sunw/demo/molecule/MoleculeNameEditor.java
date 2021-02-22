package ch22.magicbeans.src.magicbeans.sunw.demo.molecule;

/**
 * Not in the Learning Java manual. Special case property editor for molecule
 * names.
 */
public class MoleculeNameEditor extends java.beans.PropertyEditorSupport {

	public String[] getTags() {
		String result[] = { "HyaluronicAcid", "benzene", "buckminsterfullerine", "cyclohexane", "ethane", "water" };
		return result;
	}

	public String getJavaInitializationString() {
		return (String) getValue();
	}
}
