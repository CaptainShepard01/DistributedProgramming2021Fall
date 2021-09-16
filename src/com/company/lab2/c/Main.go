package main

import (
	"fmt"
	"math/rand"
	"time"
)

const numberOfMonks = 50000

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

func fight(m1 Monk, m2 Monk) Monk {
	if m1.monastery == m2.monastery {
		return Monk{monastery: m1.monastery, energy: m1.energy + m2.energy}
	}

	if m1.energy >= m2.energy {
		return Monk{monastery: m1.monastery, energy: m1.energy - m2.energy}
	}

	return Monk{monastery: m2.monastery, energy: m2.energy - m1.energy}
}

func determineWinner(monks []Monk, out chan<- Monk) {
	if len(monks) == 0 {
		return
	}

	if len(monks) == 1 {
		out <- monks[0]
		return
	}
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

	go determineWinner(monks[:len(monks)/2], c1)
	go determineWinner(monks[len(monks)/2:], c2)

	winner := fight(<-c1, <-c2)
	close(c1)
	close(c2)
	out <- winner
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
