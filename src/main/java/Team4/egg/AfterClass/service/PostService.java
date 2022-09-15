package Team4.egg.AfterClass.service;


import Team4.egg.AfterClass.entity.Member;
import Team4.egg.AfterClass.entity.Post;
import Team4.egg.AfterClass.entity.StudyGroup;
import Team4.egg.AfterClass.repository.PostRepository;
import Team4.egg.AfterClass.repository.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    public final PostRepository postRepository;
    public final StudyGroupService studyGroupService;
    private final ImageService imageService;

    @Transactional
    public void createPost(StudyGroup sGroup, Member member, Post newPost, MultipartFile photo){

        newPost.setMember(member);
        newPost.setStudyGroup(sGroup);
        //hacer verficaciones para titulo y content
        if (!photo.isEmpty()) newPost.setFile(imageService.copyFilePost(photo));
        postRepository.save(newPost);

    }

    @Transactional
    public void updatePost(Post modifiedPost) {
        Post post = postRepository.findById(modifiedPost.getId()).get();

        post.setTittle(modifiedPost.getTittle());
        post.setContent(modifiedPost.getContent());
        //post.setFile(modifiedPost.getFile());
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Post getById(Integer id) {
        return postRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Post> getAllByGroup(int idGroup) {
        List<Post> posts = postRepository.findAllByIdGroup(idGroup);
        Collections.sort(posts);
        return posts;
    }

    @Transactional
    public void deleteById(Integer id) { postRepository.deleteById(id);}

}
