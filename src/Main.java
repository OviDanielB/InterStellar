import Controller.CSVController;
import DAO.FluxRepository;
import DAO.GalaxyRepository;
import Model.*;
import View.GalaxyResultBean;
import utils.CSVFileType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.*;
import java.util.List;

/**
 * Created by ovidiudanielbarba on 09/08/16.
 */
public class Main {

    public static void main(String[] args){


        /*

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("InterStellar");
        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        Ascension ascension = new Ascension();
        ascension.setAscensionHour(2);
        ascension.setAscensionMin(3);
        ascension.setAscensionSec(2.1f);

        Declination declination = new Declination();
        declination.setDeclinationDegrees(32);
        declination.setDeclinationMin(2);
        declination.setDeclinationSign('+');
        declination.setDeclinationSec(4.3f);

        SpacePosition spacePosition = new SpacePosition();
        spacePosition.setAscension(ascension);
        spacePosition.setDeclination(declination);

        Distance distance = new Distance();
        distance.setRedshift(23.4f);
        distance.setDistanceValue(23.2f);



        Galaxy galaxy = new Galaxy();
        galaxy.setAlternativeName("sefda");
        galaxy.setName("milky");
        galaxy.setSpacePosition(spacePosition);
        galaxy.setSpectralClassification("h2");
        galaxy.setDistance(distance);


        entityManager.persist(galaxy);


        entityManager.getTransaction().commit();


*/








        //GalaxyRepository.findInRange(position,0);
        /*
        for(GalaxyResultBean g : result){
            System.out.println(g.getName());
        }
        */




        CSVController controller = CSVController.getInstance();


        /*
        controller.processCSVDataFile(new File("/Users/ovidiudanielbarba/IdeaProjects/InterStellar/src/res/MRTable3_sample.csv"));

        controller.processCSVDataFile(new File("/Users/ovidiudanielbarba/IdeaProjects/InterStellar/src/res/MRTable4_flux.csv"));
        controller.processCSVDataFile(new File("/Users/ovidiudanielbarba/IdeaProjects/InterStellar/src/res/MRTable6_cont.csv"));


        controller.processCSVDataFile(new File("/Users/ovidiudanielbarba/IdeaProjects/InterStellar/src/res/MRTable8_irs.csv"));
        controller.processCSVDataFile(new File("/Users/ovidiudanielbarba/IdeaProjects/InterStellar/src/res/MRTable11_C_3x3_5x5_flux.csv"));

*/

        //FluxRepository.findFluxRatio("IRAS08311-2459",Atom.OIII52,Atom.NIII57);

        Galaxy nG = GalaxyRepository.findByName("milky");
        System.out.println("QUERY " + nG.toString());
    }

    private static void readCSV() {


    }
}
