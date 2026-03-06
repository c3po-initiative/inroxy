package inroxy.controller

import jakarta.servlet.http.HttpServletRequest

fun HttpServletRequest.extractCookies(): String =
    getHeader("Cookie") ?: ""
