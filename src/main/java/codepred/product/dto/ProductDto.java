package codepred.product.dto;

import lombok.Data;

@Data
public class ProductDto {

    private String productName;


    private String category;


    private byte[] file1;


    private byte[] file2;


    private boolean isEnabled;
}
