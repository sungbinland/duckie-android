package land.sungbin.androidprojecttemplate.domain.usecase.upsert

import im.toss.util.tuid.tuid
import java.util.Date
import land.sungbin.androidprojecttemplate.domain.model.Comment
import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.model.common.Content
import land.sungbin.androidprojecttemplate.domain.model.util.FK
import land.sungbin.androidprojecttemplate.domain.repository.DuckUpsertRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult
import land.sungbin.androidprojecttemplate.domain.repository.result.runDuckApiCatching

class UpsertCommentUseCase(
    private val repository: DuckUpsertRepository,
) {
    /**
     * [댓글][Comment] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param ownerId 댓글을 작성한 [유저의 아이디][User.nickname]
     * @param feedId 댓글이 작성된 [피드의 아이디][Feed.id]
     * @param content 댓글의 내용
     * @param createdAt 댓글이 작성된 시간
     *
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend operator fun invoke(
        @FK ownerId: String,
        @FK feedId: String,
        content: Content,
        createdAt: Date,
    ) = runDuckApiCatching {
        repository.upsertComment(
            comment = Comment(
                id = tuid(),
                ownerId = ownerId,
                feedId = feedId,
                content = content,
                createdAt = createdAt,
            ),
        )
    }
}
