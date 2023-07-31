package studio.hcmc.board.service

import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Transaction
import studio.hcmc.board.dto.BoardDTO
import studio.hcmc.board.entity.BoardEntity
import studio.hcmc.board.table.BoardTable
import studio.hcmc.exposed.transaction.suspendedTransaction

object BoardService {
    suspend fun add(
        dto: BoardDTO.Post,
        transaction: Transaction? = null
    ) = suspendedTransaction(transaction) {
        BoardEntity.new {
            fromDataTransferObject(dto)
        }
    }

    suspend fun set(
        id: Long,
        dto: BoardDTO.Put,
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
        BoardEntity.findById(id)
    }

    suspend fun getByName(
        name: String,
        transaction: Transaction? = null
    ) = suspendedTransaction(transaction) {
        BoardEntity.find(BoardTable.name eq name).firstOrNull()
    }

    suspend fun listAll(
        offset: Long,
        size: Int,
        sortOrder: SortOrder,
        transaction: Transaction? = null
    ) = suspendedTransaction(transaction) {
        BoardEntity.all()
            .orderBy(BoardTable.id to sortOrder)
            .limit(size, offset)
    }

    suspend fun countAll(
        transaction: Transaction? = null
    ) = suspendedTransaction(transaction) {
        BoardEntity.count(Op.TRUE)
    }
}