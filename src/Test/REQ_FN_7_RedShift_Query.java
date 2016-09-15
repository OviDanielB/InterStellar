package Test;

import View.GalaxyQueryBean;
import View.GalaxyResultBean;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * REQ-FN-7 Query galaxy by redshift (less/equal, greater/equal), ordered by redshift
 */
public class REQ_FN_7_RedShift_Query {

    @Test
    public void getGalaxiesByLessRedshift() throws Exception {

        GalaxyQueryBean queryBean = new GalaxyQueryBean();
        queryBean.setOperation("REDSHIFT");
        queryBean.setRedshiftOpSign("<=");
        // redshift maximum limit
        queryBean.setRedshift("0.5");
        queryBean.setMany("5");

        List<GalaxyResultBean> result = queryBean.performQuery();

        int size = result.size();
        assertTrue(size <= 5);

        int i = 0;
        for(GalaxyResultBean r: result){
            assertTrue(r.getRedshift() <= 0.5);
            if(!(i == size - 1)){
                // ordering test
                assertTrue(result.get(i).getRedshift() <= result.get(i + 1).getRedshift() );
            }
            i++;
        }

    }

    @Test
    public void getGalaxiesByGreaterRedshift() throws Exception {

        GalaxyQueryBean queryBean = new GalaxyQueryBean();
        queryBean.setOperation("REDSHIFT");
        queryBean.setRedshiftOpSign(">=");
        // redshift minimum limit
        queryBean.setRedshift("0.5");
        queryBean.setMany("5");

        List<GalaxyResultBean> result = queryBean.performQuery();

        int size = result.size();
        assertTrue(size <= 5);

        int i = 0;
        for (GalaxyResultBean r : result) {
            assertTrue(r.getRedshift() >= 0.5);
            if(!(i == size - 1)){
                // ordering test
                assertTrue(result.get(i).getRedshift() <= result.get(i + 1).getRedshift() );
            }
            i++;
        }
    }

}