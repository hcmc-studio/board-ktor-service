package studio.hcmc.board.service

import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Transaction
import studio.hcmc.board.dto.CommentDTO
import studio.hcmc.board.entity.CommentEntity
import studio.hcmc.board.table.CommentTable
import studio.hcmc.exposed.transaction.suspendedTransaction

object CommentService {
    suspend fun add(
        articleId: Long,
        writerAddress: String,
        dto: CommentDTO.Post,
        transaction: Transaction? = null
    ) = suspendedTransaction(transaction) {
        CommentEntity.new {
            fromDataTransferObject(dto)
            this.articleId = articleId
            this.writerAddress = writerAddress
        }
    }

    suspend fun set(
        id: Long,
        dto: CommentDTO.Put,
        transaction: Transaction? = null
    ) = suspendedTransaction(transaction) {
        get(id, this)?.apply {
            fromDataTransferObject(dto)
        }
    }

    suspend fun get(
        id: Long,
        transaction: Transaction? = null
    ) = suspendedTransaction(transaction) {
        CommentEntity.findById(id)
    }

    suspend fun listAll(
        offset: Long,
        size: Int,
        sortOrder: SortOrder,
        transaction: Transaction? = null
    ) = suspendedTransaction(transaction) {
        CommentEntity.all()
            .orderBy(CommentTable.id to sortOrder)
            .limit(size, offset)
    }

    suspend fun listByArticleId(
        articleId: Long,
        offset: Long,
        size: Int,
        sortOrder: SortOrder,
        transaction: Transaction? = null
    ) = suspendedTransaction(transaction) {
        CommentEntity.find { CommentTable.articleId eq articleId }
            .orderBy(CommentTable.id to sortOrder)
            .limit(size, offset)
    }

    suspend fun countByArticleId(
        articleId: Long,
        transaction: Transaction? = null
    ) = suspendedTransaction(transaction) {
        CommentEntity.count(CommentTable.articleId eq articleId)
    }
}