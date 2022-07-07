import java.lang.Math.random
import kotlin.math.*

var countofcorrectans = mutableListOf<Int>(0,0,0,0,0,0,0,0,0,0) //0~9までの数字について正解の数字がそれぞれ何個ずつあるのかを数えて収納
var countofyournumber = mutableListOf<Int>(0,0,0,0,0,0,0,0,0,0) //0~9までの数字について自分の答えた数字がそれぞれ何個ずつあるのかを数えて収納
var numofperf = 0//完全一致の数
var numofcorrect = 0 //文字列の中のn文字目に正解の数字が何個あるか
var numoftrials = 1 //挑戦回数をカウントする変数

var inputnumber = ""//入力した文字列を収納する文字列変数

//初期設定で自分の答えを ____ にする
var yourans = mutableListOf<String>("_","_","_","_","_","_","_","_","_","_")//自分の答えた数字が正解していれば_から数字に変わるlist
var digitlimit = "" //答えの桁数の上限
var digitofans = 0 //答えの桁数
var digittemp = 1L // randnumberを決める際の一時的な変数
var randnumber = "" //正解の数字を文字列で格納
var historynumber = ""//履歴を収納する文字列
var discoverynum = "" //発見した文字列を収納する変数

fun main(){

    determineDigit() //桁数を決定
    determineRand() //正解の数字をランダムに抽選

    inputnumber = readLine()!!
    historynumber += "$inputnumber "

    while(inputnumber != randnumber) {//正解するまで繰り返す
        println("$numoftrials 回目のトライアルが終わりました")
        numoftrials++ //試行回数をカウントする
        for(n in 0 .. 9) countofyournumber[n] = inputnumber.count{ it == (n+48).toChar() }//inputnumberの中に0~9までの数字がそれぞれ何個あるのかを判定
        for(n in 0 .. 9){
            if(min(countofyournumber[n],countofcorrectans[n]) != 0) {
                discoverynum += "$n"+"を${min(countofyournumber[n],countofcorrectans[n])}個発見 "  //発見した文字列を収納
            }
            numofcorrect += min(countofyournumber[n], countofcorrectans[n])//文字列の中の文字ごとに数字が何個あるのかをカウントしたものと正解の数字が何個あるのかを比較
        }

        if(inputnumber.length < randnumber.length) inputnumber = inputnumber.padEnd(digitofans,'x')
        else if(inputnumber.length > randnumber.length) inputnumber = inputnumber.dropLast(inputnumber.length - digitofans)

        for (i in 0 until digitofans) {
            if (inputnumber[i] == randnumber[i]) {//入力した文字列と正解の文字列を比較
                yourans[i] = randnumber[i].toString()//正解した文字列のみを格納して_を数字に変えたlist
                numofperf++ //正解数のカウントを一つ増やす
            }
        }
        historynumber += " Correct $numofperf  Close ${numofcorrect-numofperf} ... $discoverynum\n"
        println(historynumber + "入力してください\n" )
        numofperf = 0
        numofcorrect = 0
        discoverynum = ""
        inputnumber = readLine()!!
        historynumber += "$inputnumber "
    }
    println("Great!!!\n" + "$numoftrials 回目の挑戦で成功しました！")
}

fun determineDigit(){
    digitlimit = "6" //桁数の上限を設定
    println("${digitlimit} 桁までの数字を入力してください")
}

// 正解の数をランダムに選択し、正解の中にどの数が何個あるのかをカウント
fun determineRand(){
    digitofans = max(3,(random()*digitlimit.toInt()+1).toInt())//digitofans にはランダムに選ばれた答えの桁数

    for (i in 1..digitofans) digittemp*=10L //digittempは桁数が正しくなっている仮置きの数で100..の形式
    //ランダムに選ばれた数を左から0埋めパディングしたものがrandnumber
    randnumber = ((digittemp* random()).toLong()).toString().padStart(digitofans,'0')

    //それぞれの文字が何個あるのかをカウントする
    for(n in 0 .. 9) countofcorrectans[n] = randnumber.count{ it == (n+48).toChar() }
}
