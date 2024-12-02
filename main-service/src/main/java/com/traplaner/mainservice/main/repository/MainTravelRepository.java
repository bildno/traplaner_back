package com.traplaner.mainservice.main.repository;

//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface MainTravelRepository
//        extends JpaRepository<Travel, Integer> {
//
//    @Query(value = """
//        SELECT new com.project.traplaner.main.dto.TopThreeFavoriteTravelDto(
//            t.id,
//            t.member.id,
//            tb.memberNickName,
//            t.title,
//            t.travelImg,
//            COUNT(f.id),
//            tb.content
//        )
//        FROM Travel t
//        JOIN t.travelBoard tb
//        LEFT JOIN tb.favorites f
//        GROUP BY t.id, t.member.id, tb.memberNickName, t.title, t.travelImg, tb.content
//        ORDER BY COUNT(f.id) DESC
//        """
//    )
//    List<TopThreeFavoriteTravelDto> findTopThreeFavoriteTravel(Pageable pageable);
//
//    @Query(value= """
//        SELECT new com.project.traplaner.main.dto.MainTravelDto(
//            t.id,
//            t.member.id,
//            t.title,
//            t.startDate,
//            t.endDate,
//            t.createAt,
//            t.updateAt,
//            t.share,
//            t.travelImg
//        )
//        FROM Travel t
//        WHERE t.member.email = :email
//    """)
//    List<MainTravelDto> findAllTravelByMemberEmail(@Param("email") String email);
//}
