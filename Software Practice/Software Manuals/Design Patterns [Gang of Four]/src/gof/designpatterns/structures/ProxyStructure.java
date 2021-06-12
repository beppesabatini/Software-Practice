package gof.designpatterns.structures;

import gof.ch04_07.proxy.*;
import gof.designpatterns.Proxy;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.Proxy Proxy} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 209. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram"><img src="../../ch04_07/proxy/image-5909.png"/>
 * </td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Subject</i></b> — corresponds to the interface
 * {@linkplain Graphic}</li>
 * <li><b><i>RealSubject</i></b> — corresponds to the implementation
 * {@linkplain Image}</li>
 * <li><b><i>Proxy</i></b> — the implementation {@linkplain ImageProxy}</li>
 * </ul>
 * </td>
 * </table>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface ProxyStructure extends Proxy {

}
