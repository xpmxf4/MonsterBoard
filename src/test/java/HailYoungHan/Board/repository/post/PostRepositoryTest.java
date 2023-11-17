package HailYoungHan.Board.repository.post;

import HailYoungHan.Board.dto.post.query.PostDbDTO;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TestEntityManager em;

    private Member member;
    private Post post;

    @BeforeEach
    void setup() {
        member = Member.builder()
                .name("Test Name")
                .email("test@example.com")
                .password("password")
                .build();
        em.persist(member);

        post = Post.builder()
                .title("Test Title")
                .content("Test Content")
                .member(member)
                .isDeleted(false) // 초기값 설정
                .build();
        em.persist(post);
    }

    @Test
    @DisplayName("지정된 멤버 ID에 대한 모든 게시글을 찾아야 한다")
    public void findPostsByMemberId_ShouldReturnPostsForGivenMemberId() throws Exception {
        // when
        List<PostDbDTO> foundPosts = postRepository.findPostsByMemberId(member.getId());

        // then
        assertThat(foundPosts).isNotEmpty();
        assertThat(foundPosts.get(0).getWriter()).isEqualTo(member.getName());
    }

    @Test
    @DisplayName("지정된 멤버 ID에 대한 삭제된 게시글을 찾아야 한다")
    public void findDeletedPostsByMemberId_ShouldReturnDeletedPostsForGivenMemberId() throws Exception {
        // given
        post = Post.builder()
                .title("Test Title")
                .content("Test Content")
                .member(member)
                .isDeleted(true)
                .build();
        em.persist(post);

        // when
        List<PostDbDTO> foundDeletedPosts = postRepository.findDeletedPostsByMemberId(member.getId());

        // then
        assertThat(foundDeletedPosts).isNotEmpty();
        assertThat(foundDeletedPosts.get(0).getIsDeleted()).isTrue();
    }

    @Test
    @DisplayName("지정된 ID에 대한 게시글 DTO를 찾아야 한다")
    public void findDTObyId_ShouldReturnPostDtoForGivenId() throws Exception {
        // when
        PostDbDTO foundPost = postRepository.findDTObyId(post.getId());

        // then
        assertThat(foundPost).isNotNull();
        assertThat(foundPost.getId()).isEqualTo(post.getId());
    }

    @Test
    @DisplayName("모든 게시글 DTO를 반환해야 한다")
    public void findAllDTOs_ShouldReturnAllPostDTOs() throws Exception {
        // when
        List<PostDbDTO> allPosts = postRepository.findAllDTOs();

        // then
        assertThat(allPosts).hasSize(1); // 초기 설정에서 하나의 포스트만 추가하였기 때문
    }

    @Test // 사실 안해도 되긴 함.
    @DisplayName("지정된 ID의 게시글을 삭제해야 한다")
    public void deletePostById_ShouldDeleteThePostForGivenId() throws Exception {
        // when
        postRepository.deletePostById(post.getId());
        em.flush(); // 실제 삭제를 적용하기 위해 사용

        // then
        assertThat(postRepository.findById(post.getId())).isNotPresent();
    }

    @Test
    @DisplayName("존재하지 않는 멤버 ID로 게시글 조회 시 빈 목록을 반환해야 한다")
    public void findPostsByNonExistingMemberId_ShouldReturnEmptyList() {
        // when
        List<PostDbDTO> result = postRepository.findPostsByMemberId(Long.MAX_VALUE);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 멤버 ID로 삭제된 게시글 조회 시 빈 목록을 반환해야 한다")
    public void findDeletedPostsByNonExistingMemberId_ShouldReturnEmptyList() {
        // when
        List<PostDbDTO> result = postRepository.findDeletedPostsByMemberId(Long.MAX_VALUE);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 게시글 ID 조회 시 null을 반환해야 한다")
    public void findDTObyNonExistingId_ShouldReturnNull() {
        // when
        PostDbDTO result = postRepository.findDTObyId(Long.MAX_VALUE);

        // then
        assertNull(result);
    }
}