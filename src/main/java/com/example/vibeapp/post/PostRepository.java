package com.example.vibeapp.post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    // JPQL을 사용하여 모든 게시글 조회 (정렬 로직 추가 가능)
    public List<Post> findAll() {
        return em.createQuery("select p from Post p order by p.no desc", Post.class)
                .getResultList();
    }

    // JPQL 페이징: setFirstResult, setMaxResults 활용
    public List<Post> findAllWithPaging(int offset, int size) {
        return em.createQuery("select p from Post p order by p.no desc", Post.class)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }

    // JPQL 집계 함수 count() 활용
    public int countAll() {
        return em.createQuery("select count(p) from Post p", Long.class)
                .getSingleResult().intValue();
    }

    // EntityManager.find()로 식별자 조회
    public Post findById(Long no) {
        return em.find(Post.class, no);
    }

    // EntityManager.persist()로 신규 엔티티 저장
    public void save(Post post) {
        em.persist(post);
    }

    // EntityManager.remove()로 엔티티 삭제
    public void delete(Long no) {
        Post post = findById(no);
        if (post != null) {
            em.remove(post);
        }
    }

    // JPA는 변경 감지(Dirty Checking)가 기본이지만, 레포지토리 수준의 명시적 update가 필요한 경우 merge 활용 가능
    public void update(Post post) {
        em.merge(post);
    }
}
