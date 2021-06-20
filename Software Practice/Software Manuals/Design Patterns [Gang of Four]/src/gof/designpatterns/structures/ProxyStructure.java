package gof.designpatterns.structures;

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
 * <td class="structure-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_07/proxy/image-5909.png"/>
 * </td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Subject</i></b> — corresponds to the interface
 * {@linkplain gof.ch04_07.proxy.Graphic Graphic}</li>
 * <li><b><i>RealSubject</i></b> — corresponds to the implementation
 * {@linkplain gof.ch04_07.proxy.Image Image}</li>
 * <li><b><i>Proxy</i></b> — the implementation
 * {@linkplain gof.ch04_07.proxy.ImageProxy ImageProxy}</li>
 * </ul>
 * </td>
 * </table>
 * 
 * <pre>
 * <style> 
 * table.javadoc-structure { 
 *     padding: 5px; width: 580px; 
 * }
 *
 * div.diagram-title { 
 *     font-size: 16px; font-weight: bold; text-align:center; 
 * }
 *
 * td.structure-diagram { 
 *     padding: 6px 0 0 0; 
 * }
 * </style>
 * </pre>
 */
public interface ProxyStructure extends Proxy {

}
