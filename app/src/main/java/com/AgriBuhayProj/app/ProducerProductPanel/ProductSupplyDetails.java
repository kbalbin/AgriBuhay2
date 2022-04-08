package com.AgriBuhayProj.app.ProducerProductPanel;

public class ProductSupplyDetails {

    public String Products,Quantity,Price,Description,ImageURL,RandomUID, ProducerId, Mobile;

    public ProductSupplyDetails(String products, String quantity, String price, String description, String imageURL, String randomUID, String producerId, String mobile) {
        Products = products;
        Quantity = quantity;
        Price = price;
        Description = description;
        ImageURL = imageURL;
        RandomUID = randomUID;
        ProducerId = producerId;
        Mobile = mobile;
    }
}
