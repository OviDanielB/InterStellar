package Test;

import Model.Atom;
import View.FluxQueryBean;
import View.FluxResultBean;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * REQ-FN-9 Flux Ratio Value by galaxy; indicate a galaxy name, two fluxes (1 at numerator, 1 at denominator) and get the ratio by aperture
 */
public class REQ_FN_9_Flux_Ratio {
    @Test
    public void getFluxRatio() throws Exception {

        FluxQueryBean bean = new FluxQueryBean();
        // flux by RATIO query
        bean.setOperation("RATIO");
        bean.setGalaxyName("NGC1097");
        bean.setFluxNum(Atom.OI63);
        bean.setFluxDen(Atom.OIII88);

        List<FluxResultBean> result = bean.performQuery();

        // expected 1 item
        for(FluxResultBean flux : result) {

            switch (flux.getAperture()) {
                case "LOW":
                    assertEquals(flux.getFluxValue(), 4.25824451, 0.0001);
                    assertFalse(flux.isNumUpperLimit());
                    assertFalse(flux.isDenUpperLimit());
                    break;
                case "CENTRAL":
                    assertEquals(flux.getFluxValue(), 4.36446667,0.0001);
                    assertFalse(flux.isNumUpperLimit());
                    assertFalse(flux.isDenUpperLimit());
                    break;
                case "HIGH":
                    assertEquals(flux.getFluxValue(),4.39859867,0.0001);
                    assertFalse(flux.isNumUpperLimit());
                    assertFalse(flux.isDenUpperLimit());
                    break;
            }
        }
    }

}