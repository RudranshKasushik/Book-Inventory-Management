package com.cpg.bim.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cpg.bim.entity.BookReview;
import com.cpg.bim.entity.Reviewer;
public interface BookReviewRepository extends JpaRepository<BookReview,String>
{
	//Check if a review exists for a given ISBN and reviewer ID
    Optional<BookReview> findByIsbnAndReviewerid(String isbn, int reviewerid);
    boolean existsByIsbnAndReviewerid(String isbn, int reviewerid);
    @Query(nativeQuery=true,value="select reviewerid from bookreview where isbn=:isbn")
    List<Integer> findReviewerIdByIsbn(String isbn);
    //List<BookReview> findByIsbn(String isbn);
    
    List<BookReview> findByIsbn(String isbn);
}