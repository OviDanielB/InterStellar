package Test;

import Model.Atom;
import View.FluxQueryBean;
import View.FluxResultBean;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * REQ-FN-11 Line on relative Continuous Flux Ratio given a specific galaxy and a specific atom
 */
public class REQ_FN_11_Line_Cont_Ratio_Flux {
    @Test
    public void getFluxRatioCont() throws Exception {
        FluxQueryBean bean = new FluxQueryBean();

        bean.setOperation("RATIO_CONT");
        bean.setGalaxyName("NGC1097");
        bean.setFluxNum(Atom.OI63);

        List<FluxResultBean> result = bean.performQuery();

        FluxResultBean resultBean = result.get(0);

        assertEquals(resultBean.getFluxValue(),6.30578,0.001);

    }

}