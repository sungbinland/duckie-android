package land.sungbin.androidprojecttemplate.domain.usecase.upsert

import land.sungbin.androidprojecttemplate.domain.model.Comment
import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.Heart
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.model.constraint.HeartTarget
import land.sungbin.androidprojecttemplate.domain.model.util.FK
import land.sungbin.androidprojecttemplate.domain.model.util.PK
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported
import land.sungbin.androidprojecttemplate.domain.repository.DuckUpsertRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult
import land.sungbin.androidprojecttemplate.domain.repository.result.runDuckApiCatching

@Unsupported
class UpsertHeartUseCase(
    private val repository: DuckUpsertRepository,
) {
    /**
     * [좋아요][Heart] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param type 좋아요 이벤트를 받은 대상 타입
     * @param commentId 좋아요 이벤트를 받은 대상 아이디.
     * [피드 아이디][Feed.id] 혹은 [댓글 아이디][Comment.id]가 될 수 있습니다.
     * @param userId 해당 이벤트를 발생시킨 [유저 아이디][User.nickname]
     *
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend operator fun invoke(
        type: HeartTarget,
        @PK @FK commentId: String,
        @PK @FK userId: String,
    ) = runDuckApiCatching {
        repository.upsertHeart(
            heart = Heart(
                type = type,
                commentId = commentId,
                userId = userId,
            ),
        )
    }
}
