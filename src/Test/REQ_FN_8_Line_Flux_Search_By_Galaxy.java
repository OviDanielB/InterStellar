package Test;

import Model.Atom;
import Model.Flux;
import View.FluxQueryBean;
import View.FluxResultBean;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * REQ-FN-8 Line flux query by galaxy; given one or more atoms (flux) and galaxy name , returns related flux values with error and upperlimit
 */
public class REQ_FN_8_Line_Flux_Search_By_Galaxy {
    @Test
    public void getFluxByGalaxyName() throws Exception {

        FluxQueryBean bean = new FluxQueryBean();
        // flux by NAME query
        bean.setOperation("NAME");
        bean.setGalaxyName("NGC1097");
        bean.addAtom(Atom.OI63);
        bean.addAtom(Atom.OIII88);

        List<FluxResultBean> result = bean.performQuery();

        // results are ordered by atoms
        FluxResultBean fOI63 = result.get(0);
        FluxResultBean fOIII88 = result.get(1);

        assertEquals(fOI63.getFluxValue(),251.79,0.01);
        assertEquals(fOI63.getError(),15.16,0.01);
        assertFalse(fOI63.isUpperLimit());

        assertEquals(fOIII88.getFluxValue(),59.13,0.01);
        assertEquals(fOIII88.getError(),5.83,0.001);
        assertFalse(fOIII88.isUpperLimit());

    }

}