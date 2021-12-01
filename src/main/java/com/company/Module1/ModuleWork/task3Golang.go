/*3.  Автостоянка. Доступно несколько машиномест. На одном месте может на-
ходиться только один автомобиль. Если все места заняты, то автомобиль не
станет ждать больше определенного времени и уедет на другую стоянку*/

package main

import (
	"fmt"
	"math/rand"
	"time"
)

func tryToPark(array []chan int) {
	size := len(array)
	for i := 0; i < 5; i++ {
		index := rand.Intn(size)

		select {
		case _, ok := <-array[index]:
			if ok {
				array[index] <- 0
				fmt.Printf("Parking lot at index %d is occupied\nTrying another one...\n", index)
			} else {
				fmt.Println("Channel closed!")
			}
		default:
			array[index] <- 0
			fmt.Printf("Parking lot at index %d is free\nParking...\n", index)
			break
		}
	}
}

func initialize() {
	PARKINGLOTS := 50
	NUMBEROFCARS := 60
	array := make([]chan int, PARKINGLOTS)

	for i := 0; i < PARKINGLOTS; i++ {
		array[i] = make(chan int, 1)
	}

	for i := 0; i < NUMBEROFCARS; i++ {
		go func() {
			tryToPark(array)
		}()
	}
	time.Sleep(5 * time.Second)
}

func main() {
	initialize()
}
