package studio.hcmc.board.service

import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Transaction
import studio.hcmc.board.dto.ArticleDTO
import studio.hcmc.board.entity.ArticleEntity
import studio.hcmc.board.table.ArticleTable
import studio.hcmc.exposed.transaction.suspendedTransaction

object ArticleService {
    suspend fun add(
        boardId: Long,
        writerAddress: String,
        dto: ArticleDTO.Post,
        transaction: Transaction? = null
    ) = suspendedTransaction(transaction) {
        ArticleEntity.new {
            fromDataTransferObject(dto)
            this.boardId = boardId
            this.writerAddress = writerAddress
        }
    }

    suspend fun set(
        id: Long,
        dto: ArticleDTO.Put,
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
        ArticleEntity.findById(id)
    }

    suspend fun listAll(
        offset: Long,
        size: Int,
        sortOrder: SortOrder,
        transaction: Transaction? = null
    ) = suspendedTransaction(transaction) {
        ArticleEntity.all()
            .orderBy(ArticleTable.id to sortOrder)
            .limit(size, offset)
    }

    suspend fun listByBoardId(
        boardId: Long,
        offset: Long,
        size: Int,
        sortOrder: SortOrder,
        transaction: Transaction? = null
    ) = suspendedTransaction(transaction) {
        ArticleEntity.find { ArticleTable.boardId eq boardId }
            .orderBy(ArticleTable.id to sortOrder)
            .limit(size, offset)
    }

    suspend fun countByBoardId(
        boardId: Long,
        transaction: Transaction? = null
    ) = suspendedTransaction(transaction) {
        ArticleEntity.count(ArticleTable.boardId eq boardId)
    }
}