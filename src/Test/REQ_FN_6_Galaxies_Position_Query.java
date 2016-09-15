package Test;

import DAO.GalaxyRepository;
import View.GalaxyQueryBean;
import View.GalaxyResultBean;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * REQ-FN-6 Search N galaxies in range from an initial position, ordered by distance
 */
public class REQ_FN_6_Galaxies_Position_Query {
    @Test
    public void getGalaxiesByPosition() throws Exception {

        GalaxyQueryBean queryBean = new GalaxyQueryBean();
        queryBean.setOperation("POSITION");
        queryBean.setAscH("0");
        queryBean.setAscM("0");
        queryBean.setAscS("0");
        queryBean.setDeclSign("+");
        queryBean.setDeclD("0");
        queryBean.setDeclM("0");
        queryBean.setDeclS("0");
        queryBean.setMany("5");

        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("NGC7679");
        expectedResult.add("NGC7603");
        expectedResult.add("NGC7592W");
        expectedResult.add("NGC7674");
        expectedResult.add("HS0017+1055");

        List<GalaxyResultBean> result = queryBean.performQuery();

        int size = result.size();
        int i = 0;
        assertTrue(expectedResult.size() <= 5);
        for(GalaxyResultBean r : result){
            assertEquals(r.getName(),expectedResult.get(result.indexOf(r)));

            if(!(i == size - 1)){
                //ordering by distance test
                assertTrue(result.get(i).getDistance() < result.get(i+1).getDistance());
            }
            i++;
        }
    }

}