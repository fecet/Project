import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author InJavaWeTrust
 *
 */
public class ProductInfo implements Serializable{

    private static final long serialVersionUID = 8179244535272774089L;



    public List<String> Comments;

    /**
     * 商品ID
     */
    private String productid;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品价格
     */
    private String productPrice;
    /**
     * 月销售笔数
     */
    private String tradeNum;
    /**
     * 商品URL
     */
    private String productUrl;
    /**
     * 商品网店名称
     */
    private String shopName;
    /**
     * 电商名称
     */
    private String ecName;
    /**
     * 爬取入库日期
     */
    private Date date;

    public String getProductid() {
        return productid;
    }
    public void setProductid(String productid) {
        this.productid = productid;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
    public String getTradeNum() {
        return tradeNum;
    }
    public void setTradeNum(String tradeNum) {
        this.tradeNum = tradeNum;
    }
    public String getProductUrl() {
        return productUrl;
    }
    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
    public String getShopName() {
        return shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public String getEcName() {
        return ecName;
    }
    public void setEcName(String ecName) {
        this.ecName = ecName;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public void setComments(int commentsType, int sortType) throws IOException {
        for (int i = 0; i < 10; i++) {
            String url = CommentConstants.JDURL
                    + productid
                    + CommentConstants.JDPRODUCT_S
                    + commentsType
                    + CommentConstants.JDPRODUCT_T
                    + sortType
                    + CommentConstants.JDPRODUCT_P
                    + i
                    + CommentConstants.JDURLLAST;
            Document document = Jsoup.connect(url).get();

            Pattern p = Pattern.compile("\\{(.*?)\\}");

            Pattern re_content = Pattern.compile("\"content\":\"(.*?)\"");

            Matcher m = p.matcher(document.body().text());

            while (m.find()){
                String str = m.group(1);
                Matcher matcher = re_content.matcher(str);

                while (matcher.find()){
                    System.out.println(matcher.group(1));
                }
            }
        }
    }

}
