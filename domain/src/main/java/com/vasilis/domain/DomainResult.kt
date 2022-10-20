package com.vasilis.domain

/**
 * Created by Vasilis Fouroulis on 18/10/22.
 * vasilisfouroulis@gmail.com
 */
sealed class DomainResult<Data> {
    data class Success<Data>(val data: Data) : DomainResult<Data>()
    data class Error<Data>(val error: String) : DomainResult<Data>()
}