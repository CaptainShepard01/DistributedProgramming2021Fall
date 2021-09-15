package main

import (
	"fmt"
	"math/rand"
	"time"
)

const numberOfMonks = 16

type Monk struct {
	energy    int
	monastery int
}

var initialArray []Monk

func generateRandomArray() {
	rand.Seed(int64(time.Now().Second()))
	for i := 0; i < numberOfMonks; i++ {
		initialArray = append(initialArray, Monk{rand.Intn(1000) + 1, rand.Intn(2)})
	}
}

func printEnergy() {
	for i := 0; i < numberOfMonks; i++ {
		fmt.Printf("{%d, %d} ", initialArray[i].energy, initialArray[i].monastery)
	}
}

func wrapper(monks []Monk, start, end int) Monk {
	ch1 := make(chan Monk)
	ch2 := make(chan Monk)

	middle := (end + start) / 2

	go determineWinner(monks, start, middle, ch1, ch2)
	go determineWinner(monks, middle+1, end, ch1, ch2)

	leftWinner := <-ch1
	rightWinner := <-ch2

	if leftWinner.energy > rightWinner.energy {
		return leftWinner
	}
	return rightWinner
}

func determineWinner(monks []Monk, start, end int, ch1, ch2 chan Monk) Monk {
	if start == end {
		ch1 <- monks[start]
	}

	//if end-start == 1 {
	//	if monks[start].energy > monks[end].energy {
	//		return monks[start]
	//	}
	//	return monks[end]
	//}

	middle := (end + start) / 2

	ch1 <- determineWinner(monks, start, middle, ch1, ch2)
	ch2 <- determineWinner(monks, middle+1, end, ch1, ch2)

	leftWinner := <-ch1
	rightWinner := <-ch2

	if leftWinner.energy > rightWinner.energy {
		return leftWinner
	}
	return rightWinner
}

func main() {
	generateRandomArray()
	printEnergy()

	winner := wrapper(initialArray, 0, numberOfMonks-1)
	fmt.Printf("\nWinner is monk with energy %d from monastery #%d\n", winner.energy, winner.monastery)
}
