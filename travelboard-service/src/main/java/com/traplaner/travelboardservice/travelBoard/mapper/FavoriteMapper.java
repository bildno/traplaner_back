package com.traplaner.travelboardservice.travelBoard.mapper;


import com.traplaner.mypage.dto.response.FavoriteListResponseDTO;
import com.project.traplaner.travelBoard.dto.PageDTO;
import com.project.traplaner.travelBoard.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FavoriteMapper {


    List<FavoriteListResponseDTO> favorite_List(@Param("memberId") int memberId, @Param("page") PageDTO page);

    // 10/28 by jhjeong
    void save(Favorite favorite);

    int getTotal(PageDTO page, int memberId);
}

