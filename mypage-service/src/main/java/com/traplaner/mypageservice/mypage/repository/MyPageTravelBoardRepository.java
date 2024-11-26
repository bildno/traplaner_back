package com.traplaner.mypageservice.mypage.repository;

import com.traplaner.mypageservice.mypage.dto.response.TravelBoardResponseDTO;
import com.traplaner.mypageservice.mypage.entity.TravelBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Quareery;\n" +
            "\n" +
            "public interface MyPageTravelBoardRepository extends JpaRepository<TravelBoard, Integer> {\n" +
            "\n" +
            "    @Query(value = \"select\" +\n" +
            "            \" tb.id, \" +\n" +
            "            \"tb.travel.id, \" +\n" +
            "            \"tb.memberNickName, \" +\n" +
            "            \"DATE_FORMAT(tb.writeDate, '%Y-%m-%d') as write_date \" +\n" +
            "            \"from TravelBoard tb join Travel t on tb.travel.id  = t.id \" +\n" +
            "            \"where tb.memberNickName = ?1 and t.sh = true")
    Page<TravelBoardResponseDTO> findByMemberNickName(String nickName, Pageable pageable);


    Long countById(long travelId);
}
