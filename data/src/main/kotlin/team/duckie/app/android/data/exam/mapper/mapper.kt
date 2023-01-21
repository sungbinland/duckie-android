/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.mapper

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import team.duckie.app.android.data.category.mapper.toDomain
import team.duckie.app.android.data.exam.model.AnswerData
import team.duckie.app.android.data.exam.model.ChoiceData
import team.duckie.app.android.data.exam.model.ExamBodyData
import team.duckie.app.android.data.exam.model.ExamData
import team.duckie.app.android.data.exam.model.ExamInstanceBodyData
import team.duckie.app.android.data.exam.model.ExamInstanceSubmitBodyData
import team.duckie.app.android.data.exam.model.ExamInstanceSubmitData
import team.duckie.app.android.data.exam.model.ExamThumbnailBodyData
import team.duckie.app.android.data.exam.model.ImageChoiceData
import team.duckie.app.android.data.exam.model.ProblemData
import team.duckie.app.android.data.exam.model.QuestionData
import team.duckie.app.android.data.tag.mapper.toDomain
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.data.user.mapper.toDomain
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.ChoiceModel
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.exam.model.ExamBody
import team.duckie.app.android.domain.exam.model.ExamInstanceBody
import team.duckie.app.android.domain.exam.model.ExamInstanceSubmit
import team.duckie.app.android.domain.exam.model.ExamInstanceSubmitBody
import team.duckie.app.android.domain.exam.model.ExamThumbnailBody
import team.duckie.app.android.domain.exam.model.ImageChoiceModel
import team.duckie.app.android.domain.exam.model.Problem
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.domain.exam.model.ShortModel
import team.duckie.app.android.util.kotlin.AllowCyclomaticComplexMethod
import team.duckie.app.android.util.kotlin.OutOfDateApi
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe
import team.duckie.app.android.util.kotlin.fastMap

@OutOfDateApi
@AllowCyclomaticComplexMethod
internal fun ExamData.toDomain() = Exam(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    title = title ?: duckieResponseFieldNpe("${this::class.java.simpleName}.title"),
    description = description
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.description"),
    thumbnailUrl = thumbnailUrl,
    buttonTitle = buttonTitle
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.buttonTitle"),
    certifyingStatement = certifyingStatement
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.certifyingStatement"),
    solvedCount = solvedCount
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.solvedCount"),
    answerRate = answerRate ?: duckieResponseFieldNpe("${this::class.java.simpleName}.answerRate"),
    category = category?.toDomain()
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.category"),
    mainTag = mainTag?.toDomain()
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.mainTag"),
    subTags = subTags?.fastMap(TagData::toDomain)?.toPersistentList() ?: persistentListOf(),
    problems = problems?.fastMap(ProblemData::toDomain)?.toImmutableList() ?: persistentListOf(),
    type = type ?: duckieResponseFieldNpe("${this::class.java.simpleName}.type"),
    user = user?.toDomain() ?: duckieResponseFieldNpe("${this::class.java.simpleName}.user"),
    status = status ?: duckieResponseFieldNpe("${this::class.java.simpleName}.status"),
)

@OutOfDateApi
internal fun ProblemData.toDomain() = Problem(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    question = question?.toDomain()
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.question"),
    answer = answer?.toDomain() ?: duckieResponseFieldNpe("${this::class.java.simpleName}.answer"),
    correctAnswer = correctAnswer
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.correctAnswer"),
    hint = hint ?: duckieResponseFieldNpe("${this::class.java.simpleName}.hint"),
    memo = memo ?: duckieResponseFieldNpe("${this::class.java.simpleName}.memo"),
)

internal fun QuestionData.toDomain() = when (this) {
    is QuestionData.Text -> Question.Text(
        text = text ?: duckieResponseFieldNpe("${this::class.java.simpleName}.text"),
    )

    is QuestionData.Image -> Question.Image(
        text = text ?: duckieResponseFieldNpe("${this::class.java.simpleName}.text"),
        imageUrl = imageUrl ?: duckieResponseFieldNpe("${this::class.java.simpleName}.imageUrl"),
    )

    is QuestionData.Audio -> Question.Audio(
        text = text ?: duckieResponseFieldNpe("${this::class.java.simpleName}.text"),
        audioUrl = audioUrl ?: duckieResponseFieldNpe("${this::class.java.simpleName}.audioUrl"),
    )

    is QuestionData.Video -> Question.Video(
        text = text ?: duckieResponseFieldNpe("${this::class.java.simpleName}.text"),
        videoUrl = videoUrl ?: duckieResponseFieldNpe("${this::class.java.simpleName}.videoUrl"),
    )
}

internal fun AnswerData.toDomain(): Answer = when (this) {
    is AnswerData.ShortAnswer -> Answer.Short(
        answer = ShortModel(
            shortAnswer ?: duckieResponseFieldNpe("${this::class.java.simpleName}.shortAnswer"),
        ),
    )

    is AnswerData.Choice -> Answer.Choice(
        choices = choices?.fastMap(ChoiceData::toDomain)?.toImmutableList()
            ?: duckieResponseFieldNpe("${this::class.java.simpleName}.choices"),
    )

    is AnswerData.ImageChoice -> Answer.ImageChoice(
        imageChoice = imageChoice?.fastMap(ImageChoiceData::toDomain)?.toImmutableList()
            ?: duckieResponseFieldNpe("${this::class.java.simpleName}.imageChoice"),
    )
}

internal fun ChoiceData.toDomain() =
    ChoiceModel(text = text ?: duckieResponseFieldNpe("${this::class.java.simpleName}.text"))

internal fun ImageChoiceData.toDomain() = ImageChoiceModel(
    text = text ?: duckieResponseFieldNpe("${this::class.java.simpleName}.text"),
    imageUrl = imageUrl ?: duckieResponseFieldNpe("${this::class.java.simpleName}.imageUrl"),
)

@OutOfDateApi
internal fun ExamBody.toData() = ExamBodyData(
    title = title,
    description = description,
    mainTagId = mainTagId,
    subTagIds = subTagIds,
    categoryId = categoryId,
    certifyingStatement = certifyingStatement,
    thumbnailImageUrl = thumbnailImageUrl,
    thumbnailType = thumbnailType?.value,
    problems = problems.fastMap(Problem::toData),
    isPublic = isPublic,
    buttonTitle = buttonTitle,
    userId = userId,
)

@OutOfDateApi
internal fun ExamThumbnailBody.toData() = ExamThumbnailBodyData(
    category = category,
    certifyingStatement = certifyingStatement,
    mainTag = mainTag,
    nickName = nickName,
    title = title,
    type = type,
)

internal fun Problem.toData() = ProblemData(
    question = question.let { question ->
        when (question) {
            is Question.Text -> QuestionData.Text(
                type = question.type.key,
                text = question.text,
            )

            is Question.Video -> QuestionData.Video(
                videoUrl = question.videoUrl,
                type = question.type.key,
                text = question.text,
            )

            is Question.Image -> QuestionData.Image(
                imageUrl = question.imageUrl,
                type = question.type.key,
                text = question.text,
            )

            is Question.Audio -> QuestionData.Audio(
                audioUrl = question.audioUrl,
                type = question.type.key,
                text = question.text,
            )
        }
    },
    answer = answer.let { answer ->
        when (answer) {
            is Answer.Short -> AnswerData.ShortAnswer(
                shortAnswer = answer.answer.text,
                type = answer.type.key,
            )

            is Answer.Choice -> AnswerData.Choice(
                choices = answer.choices.map {
                    it.toData()
                }.toList(),
                type = answer.type.key,
            )

            is Answer.ImageChoice -> AnswerData.ImageChoice(
                imageChoice = answer.imageChoice.map {
                    it.toData()
                }.toList(),
                type = answer.type.key,
            )
        }
    },
    correctAnswer = correctAnswer,
    hint = hint,
    memo = memo,
)

internal fun ChoiceModel.toData() = ChoiceData(text = text)

internal fun ImageChoiceModel.toData() = ImageChoiceData(
    text = text,
    imageUrl = imageUrl,
)

@OutOfDateApi
internal fun ExamInstanceBody.toData() = ExamInstanceBodyData(
    examId = this.examId,
)

@OutOfDateApi
internal fun ExamInstanceSubmitBody.toData() = ExamInstanceSubmitBodyData(
    submitted = this.submitted,
)

internal fun ExamInstanceSubmitData.toDomain() = ExamInstanceSubmit(
    message = message ?: duckieResponseFieldNpe("${this::class.java.simpleName}.message"),
    results = results ?: duckieResponseFieldNpe("${this::class.java.simpleName}.results"),
)
