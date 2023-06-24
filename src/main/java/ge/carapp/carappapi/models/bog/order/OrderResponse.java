package ge.carapp.carappapi.models.bog.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;


/**
 * <pre class="code">
 *   {
 *         "id": "{order_id}",
 *         "_links": {
 *             "details": {
 *                 "href": "https://api.bog.ge/payments/v1/receipt/{order_id}"
 *             },
 *             "redirect": {
 *                 "href": "https://payment.bog.ge/?order_id={order_id}"
 *             }
 *         }
 *     }
 *  </pre>
 *
 * @param id
 */
public record OrderResponse(
    String id,

    @JsonProperty("_links")
    OrderLinks links
) {
}
