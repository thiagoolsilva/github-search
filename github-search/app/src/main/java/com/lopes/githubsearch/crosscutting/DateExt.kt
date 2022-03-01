/*
 *  Copyright (c) 2022  Thiago Lopes da Silva
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.lopes.githubsearch.crosscutting

import java.util.Calendar
import java.util.Date

/**
 * Return difference between two dates in seconds
 * @return difference in hours
 */
fun Date.diffInHour(refHour: Date): Long {
    val diffInMilliseconds = this.time - refHour.time
    val seconds = diffInMilliseconds / 1000
    val minutes = seconds / 60
    return minutes / 60
}

/**
 * Return difference between two dates in seconds
 * @return difference in minutes
 */
fun Date.diffInMinutes(refHour: Date): Long {
    val diffInMilliseconds = this.time - refHour.time
    val seconds = diffInMilliseconds / 1000
    return seconds / 60
}

/**
 * Return difference between two dates in seconds
 * @return difference in seconds
 */
fun Date.diffInSeconds(refHour: Date): Long {
    val diffInMilliseconds = this.time - refHour.time
    return diffInMilliseconds / 1000
}

fun Date.addHour(hour: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this.time
    calendar.add(Calendar.HOUR, hour)
    return calendar.time
}

fun Date.addMinute(minute: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this.time
    calendar.add(Calendar.MINUTE, minute)
    return calendar.time
}
