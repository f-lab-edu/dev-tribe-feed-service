package com.devtribe.unit.tag.application

import com.devtribe.domain.tag.appliction.TagService
import com.devtribe.domain.tag.appliction.dtos.TagCreateRequest
import com.devtribe.domain.tag.appliction.validators.TagRequestValidator
import com.devtribe.domain.tag.dao.TagRepository
import com.devtribe.domain.tag.entity.Tag
import com.devtribe.fixtures.tag.domain.TagFixture
import org.springframework.dao.DataIntegrityViolationException
import spock.lang.Specification
import spock.lang.Subject

class TagServiceTest extends Specification {

    def tagRepository = Mock(TagRepository)
    def tagRequestValidator = Mock(TagRequestValidator)

    @Subject
    TagService tagService = new TagService(
            tagRepository,
            tagRequestValidator
    )

    def "태그 저장 성공"() {
        given:
        def request = new TagCreateRequest("backend")
        tagRepository.save(_ as Tag) >> TagFixture.createTag(id: 1L, name: "backend")

        when:
        def result = tagService.createTag(request)

        then:
        result != null
        result == 1L
    }

    def "태그 저장 실패 - 태그 최대 길이 초과"() {
        given:
        def request = new TagCreateRequest("backend is gooooood")
        tagRequestValidator.validateTagNameLength(_ as String) >> { throw new IllegalArgumentException() }

        when:
        tagService.createTag(request)

        then:
        thrown(IllegalArgumentException.class)
    }

    def "태그 저장 실패 - 이미 존재하는 태그 이름"() {
        given:
        def request = new TagCreateRequest("existTag")
        tagRepository.save(_ as Tag) >> { throw new DataIntegrityViolationException("이미 존재하는 태그 이름") }

        when:
        tagService.createTag(request)

        then:
        def e = thrown(DataIntegrityViolationException.class)
        e.message == "이미 존재하는 태그 이름"
    }

    def "태그 개별 조회 성공"() {
        given:
        def tagId = 1L
        tagRepository.findById(_ as Long) >> Optional.of(TagFixture.createTag(id: tagId, name: "backend"))

        when:
        def tag = tagService.findTagById(tagId)

        then:
        tag != null
        tag.id == tagId
        tag.name == "backend"
    }

    def "태그 개별 조회 실패 - 존재하지 않는 태그 아이디"() {
        given:
        def tagId = 9999
        tagRepository.findById(_ as Long) >> Optional.empty()

        when:
        tagService.findTagById(tagId)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "요청한 태그가 존재하지 않습니다."
    }

}