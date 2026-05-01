package com.mycompany.myproject.demo.concurrency

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

// 模拟一个后端服务类
class InventoryService {

    // 结构化并发：使用 coroutineScope 确保内部所有任务完成后才返回
    suspend fun getProductDetails(productId: String): String = coroutineScope {
        println("开始查询产品 $productId 的完整信息...")

        // async 开启异步并行任务
        val stockDeferred = async(Dispatchers.IO) {
            fetchStockLevel(productId)
        }
        val priceDeferred = async(Dispatchers.IO) {
            fetchPrice(productId)
        }

        // 等待两个结果（并行执行，总耗时等于最慢的那个）
        val stock = stockDeferred.await()
        val price = priceDeferred.await()
        val something = stock + price

        "产品: $productId, 库存: $stock, 价格: $price, 其他: $something"
    }

    // 模拟耗时的网络 IO 1
    private suspend fun fetchStockLevel(productId: String): Int {
        delay(5000) // 模拟网络延迟
        println("库存服务返回结果")
        return 150
    }

    // 模拟耗时的网络 IO 2
    private suspend fun fetchPrice(productId: String): Double {
        delay(800) // 模拟网络延迟
        println("价格服务返回结果")
        return 99.99
    }
}

fun main() = runBlocking {
    val service = InventoryService()

    val time = measureTimeMillis {
        val result = service.getProductDetails("TARGET-SKU-123")
        println("最终结果: $result")
    }

    println("总计耗时: ${time}ms")
    // 输出约 1000ms 左右，证明是并行而非串行（1000+800）
}