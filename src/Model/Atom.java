package Model;
/**
 * enumeration of all available atoms for measurements
 */
public enum Atom {
    NeV14("NeV14.3"),
    NeV24("NeV24.3"),
    OIV25("OIV25.9"),
    OIII52("OIII52"),
    NIII57("NIII57"),
    OI63("OI63"),
    OIII88("OIII88"),
    NII122("NII122"),
    OI145("OI145"),
    CII158("CII158"),
    SIV10("SIV10.5"),
    NeII12("NeII12.8"),
    NeIII15("NeIII15.6"),
    SIII18("SIII18.7"),
    SIII33("SIII33.5"),
    SiII34("SiII34.8");

    private String atom;

    Atom(String atom) {
        this.atom = atom;
    }

    public String getAtom() {
        return atom;
    }
}