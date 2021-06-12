package gof.designpatterns;

/**
 * <table class="javadoc-text">
 * <tr>
 * <td>An instance of the <b>Proxy</b> pattern, described in <i>Design
 * Patterns</i> [{@linkplain GangOfFour Gang of Four}], pp. 207-217. Provide a
 * surrogate or placeholder for another object to control access to it.</td>
 * </tr>
 * <tr>
 * <td>• <b><i>remote proxies</i></b> are responsible for encoding a request and
 * its arguments and for sending the encoded request to the real subject in a
 * different address space.</td>
 * </tr>
 * <tr>
 * <td>• <b><i>virtual proxies</i></b> may cache additional information about
 * the real subject so that they can postpone accessing it. For example, the
 * ImageProxy caches the real Image's extent or dimensions.</td>
 * </tr>
 * <tr>
 * <td>• <b><i>protection proxies</i></b> check that the caller has the access
 * permissions required to perform a request.</td>
 * </tr>
 * <tr>
 * <td>See also the {@linkplain gof.designpatterns.structures.ProxyStructure
 * Proxy Structure} diagram.</td>
 * </tr>
 * </table>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="../ch04_07/proxy/UML Diagram.jpg" />
 * </div> <link rel="stylesheet" href="../styles/gof.css">
 */
public interface Proxy extends GangOfFour {

}
