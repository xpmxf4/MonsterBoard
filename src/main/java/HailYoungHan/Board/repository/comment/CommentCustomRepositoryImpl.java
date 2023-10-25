package HailYoungHan.Board.repository.comment;

import HailYoungHan.Board.dto.comment.query.CommentDbDTO;
import HailYoungHan.Board.dto.comment.query.QCommentDbDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;

import javax.persistence.EntityManager;

import java.util.List;

import static HailYoungHan.Board.entity.QComment.*;


public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final JPAQueryFactory queryFactory;

    public CommentCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

//    @Override
//    public void updateCommentDTO(Long commentId, CommentDbDTO dto) {
//
//        JPAUpdateClause updateClause = queryFactory.update(comment).where(comment.id.eq(commentId));
//
//        // content 변경 체크
//        if (dto.getContent() != null)
//            updateClause.set(comment.content, dto.getContent());
//
//        // isDeleted 변경 체크
//        if (dto.getIsDeleted() != null)
//            updateClause.set(comment.isDeleted, dto.getIsDeleted());
//
//        updateClause.execute();
//    }

    @Override
    public CommentDbDTO findDTOById(Long commentId) {

        return queryFactory
                .select(new QCommentDbDTO(
                        comment.content,
                        comment.isDeleted
                ))
                .from(comment)
                .where(comment.id.eq(commentId))
                .fetchOne();

    }

    @Override
    public List<CommentDbDTO> findAllDTOs() {

        return queryFactory
                .select(new QCommentDbDTO(
                        comment.content,
                        comment.isDeleted
                ))
                .from(comment)
                .fetch();
    }

    @Override
    public List<CommentDbDTO> findAllDTOsByMemberId(Long memberId, boolean isDeleted) {
        return queryFactory
                .select(new QCommentDbDTO(
                        comment.content,
                        comment.isDeleted
                ))
                .from(comment)
                .where(comment.member.id.eq(memberId).and(comment.isDeleted.eq(isDeleted)))
                .fetch();
    }

    @Override
    public List<CommentDbDTO> findAllDTOsByMemberIdAndIsDeleted(Long memberId){

        return queryFactory
                .select(new QCommentDbDTO(
                        comment.content,
                        comment.isDeleted
                ))
                .from(comment)
                .where(comment.member.id.eq(memberId).and(comment.isDeleted.eq(true)))
                .fetch();
    }
}
