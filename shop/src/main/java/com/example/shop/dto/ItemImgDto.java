package com.example.shop.dto;

import com.example.shop.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ItemImgDto {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    //static 키워드를 사용함으로써 modelMapper 인스턴스를 여러 ItemImgDto 객체가 공유하고, 이를 통해 자원을 효율적으로 관리하고 성능을 최적화할 수 있습니다.
    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImg){ //static 메소드로 선언해 객체를 생성하지 않아도 호출 가능
        return modelMapper.map(itemImg, ItemImgDto.class);
    }
}
