package Test;

import DAO.GalaxyRepository;
import DAO.LuminosityRepository;
import DAO.MetallicityRepository;
import Model.*;
import View.GalaxyQueryBean;
import View.GalaxyResultBean;
import org.junit.Test;

import java.util.List;
import java.util.StringJoiner;

import static org.junit.Assert.*;

/**
 * REQ-FN-5 Query Galaxy By Name Test; choose a known galaxy and query by name and compare results
 * IMPORTANT : database must include before MRTable3_sample.csv file to have data on galaxy
 */
public class REQ_FN_5_Galaxy_By_Name_Query {
    @Test
    public void getGalaxiesByName() throws Exception {

        // insert dummy galaxy
        Ascension ascension = new Ascension();
        ascension.setAscensionHour(0);
        ascension.setAscensionMin(3);
        ascension.setAscensionSec(9.6038f);

        Declination declination = new Declination();
        declination.setDeclinationDegrees(21);
        declination.setDeclinationMin(57);
        declination.setDeclinationSign('+');
        declination.setDeclinationSec(36.8064f);

        SpacePosition spacePosition = new SpacePosition();
        spacePosition.setAscension(ascension);
        spacePosition.setDeclination(declination);

        Distance distance = new Distance();
        distance.setRedshift(0.0219450f);
        distance.setDistanceValue(96.600f);

        Galaxy galaxy = new Galaxy();
        galaxy.setAlternativeName("UGC00006");
        galaxy.setName("Mrk334");
        galaxy.setSpacePosition(spacePosition);
        galaxy.setSpectralClassification("S1.8");
        galaxy.setDistance(distance);

        Luminosity luminosity = new Luminosity();
        luminosity.setGalaxy(galaxy);
        luminosity.setAtom(Atom.NeV14);
        luminosity.setValue(41.16);

        Luminosity luminosity1 = new Luminosity();
        luminosity1.setGalaxy(galaxy);
        luminosity1.setAtom(Atom.NeV24);
        luminosity1.setValue(41.09);

        Luminosity luminosity2 = new Luminosity();
        luminosity2.setGalaxy(galaxy);
        luminosity2.setAtom(Atom.OIV25);
        luminosity2.setValue(41.22);

        Metallicity metallicity = new Metallicity();
        metallicity.setGalaxy(galaxy);
        // no metallicity specified for galaxy
        metallicity.setValue(-1);
        metallicity.setError(-1);


        GalaxyQueryBean queryBean = new GalaxyQueryBean();
        //Name query operation
        queryBean.setOperation("NAME");
        queryBean.setGalaxyName("Mrk334");

        List<GalaxyResultBean> result = queryBean.performQuery();

        // there could be multiple results where galaxy name LIKE TestGalaxy but in this case there aren't
        GalaxyResultBean resultBean = result.get(0);

        assertTrue(resultBean.getName().equals("Mrk334"));
        // luminosity value must average of three luminosity
        assertEquals(resultBean.getLumAvg(),(luminosity.getValue() + luminosity1.getValue() +luminosity2.getValue())/3,0.01);
        assertEquals(resultBean.getMetallicity(),metallicity.getValue(),0.1);
        assertEquals(resultBean.getDistance(),galaxy.getDistance().getDistanceValue(),0.01);
        assertEquals(resultBean.getRedshift(),galaxy.getDistance().getRedshift(),0.01);

        assertEquals(resultBean.getAscH(),galaxy.getSpacePosition().getAscension().getAscensionHour());
        assertEquals(resultBean.getAscM(),galaxy.getSpacePosition().getAscension().getAscensionMin());
        assertEquals(resultBean.getAscS(),galaxy.getSpacePosition().getAscension().getAscensionSec(),0.01);

        assertEquals(resultBean.getDecSign(),galaxy.getSpacePosition().getDeclination().getDeclinationSign());
        assertEquals(resultBean.getDecD(),galaxy.getSpacePosition().getDeclination().getDeclinationDegrees());
        assertEquals(resultBean.getDecM(),galaxy.getSpacePosition().getDeclination().getDeclinationMin());
        assertEquals(resultBean.getDecS(),galaxy.getSpacePosition().getDeclination().getDeclinationSec(),0.01);


    }

}