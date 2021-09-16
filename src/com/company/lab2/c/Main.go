package main

import (
	"fmt"
	"math/rand"
	"time"
)

const numberOfMonks = 50

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

func determineWinner(monks []Monk, out chan<- Monk) {
	if len(monks) == 0 {
		return
	}

	if len(monks) == 1 {
		out <- monks[0]
	}
	length := len(monks)

	c1 := make(chan Monk, 10)
	c2 := make(chan Monk, 10)

	/*var group sync.WaitGroup
	group.Add(2)

	go func() {
		group.Wait()

	}()

	go func() {
		defer group.Done()
		determineWinner(monks[:length/2], c1)
	}()

	go func() {
		defer group.Done()
		determineWinner(monks[length/2:], c2)
	}()*/

	go determineWinner(monks[:length/2], c1)
	go determineWinner(monks[length/2:], c2)

	leftWinner := <-c1
	rightWinner := <-c2

	close(c1)
	close(c2)

	if leftWinner.energy > rightWinner.energy {
		out <- leftWinner
	}
	out <- rightWinner
}

func main() {
	generateRandomArray()
	//printEnergy()

	outputChannel := make(chan Monk, 10)

	start := time.Now()
	determineWinner(initialArray, outputChannel)

	winnerMonk := <-outputChannel
	elapsedTime := time.Since(start)

	close(outputChannel)
	fmt.Printf("\nWinner is monk with energy %d from monastery #%d\n", winnerMonk.energy, winnerMonk.monastery)
	fmt.Printf("Time elapsed: %s\n", elapsedTime)
}
