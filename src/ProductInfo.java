import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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

    ProductInfo(){
        this.comments = new ArrayList<>();
    }


    /*
    * 商品评论
    * */
    private List<String> comments;

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


    public void setComments(int commentsType, int sortType) throws IOException {
        /*
        * 默认爬取十页评论
        *
        * */
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
//            System.out.println(url);

            Document document = Jsoup.connect(url).get();


            Pattern p = Pattern.compile("\\{(.*?)\\}");

            Pattern re_content = Pattern.compile("\"content\":\"(.*?)\"");

            Matcher m = p.matcher(document.body().text());

            while (m.find()){
                String str = m.group(1);
                Matcher matcher = re_content.matcher(str);

                while (matcher.find()){
                    String com = matcher.group(1);
                    comments.add(com);
                }
            }
        }
    }

    public List<String> getComments() {
        return comments;
    }

}
