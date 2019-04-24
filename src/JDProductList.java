import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author InJavaWeTrust
 *
 */
public class JDProductList implements ProductList{

    private String jdUrl;

    private String productName;

    private static PriceCheckUtil pcu = PriceCheckUtil.getInstance();

    public JDProductList(String jdUrl, String productName){
        this.jdUrl = jdUrl;
        this.productName = productName;
    }

    @Override
    public List<ProductInfo> getProductList() {
        List<ProductInfo> jdProductList = new ArrayList<>();
        ProductInfo productInfo;
        String url = "";
        for(int i = 0; i < 1; i++){
            try {
                System.out.println("JD Product 第[" + (i + 1) + "]页");
                if(i == 0) {
                    url = jdUrl;
                }else{
                    url = Constants.JDURL + pcu.getGbk(productName) + Constants.JDENC + Constants.JDPAGE + (i + 1);
                }
                System.out.println(url);
                Document document = Jsoup.connect(url).timeout(5000).get();
                Elements uls = document.select("ul[class=gl-warp clearfix]");
                for (Element ul : uls) {
                    Elements lis = ul.select("li[data-sku]");
                    for (Element li : lis) {
                        Element div = li.select("div[class=gl-i-wrap]").first();
                        Elements title = div.select("div[class=p-name p-name-type-2]>a");
                        String productName = title.attr("title"); //得到商品名称
                        Elements price = div.select("div[class=p-price]");
                        String productPrice = price.text(); //得到商品价格
                        String productID = li.attr("data-pid");
                        productInfo = new ProductInfo();
                        productInfo.setProductName(productName);
                        productInfo.setProductPrice(productPrice);
                        productInfo.setProductid(productID);
                        jdProductList.add(productInfo);
                    }
                }
            } catch(Exception e) {
                System.out.println("Get JD product has error [" + url + "]");
                System.out.println(e.getMessage());
            }
        }
        return jdProductList;
    }

    public static void main(String[] args) {
        try {
            String productName = "书包";
            String jdUrl = Constants.JDURL + pcu.getGbk(productName)  + Constants.JDENC;
            List<ProductInfo> list = new JDProductList(jdUrl, productName).getProductList();
            System.out.println(list.size());
            for(ProductInfo pi : list){
                System.out.println(pi.getProductName() + "  " + pi.getProductPrice()+" "+pi.getProductid());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
