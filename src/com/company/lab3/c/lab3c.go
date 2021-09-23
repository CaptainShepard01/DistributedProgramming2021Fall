package main

type Smoker struct {
	typeOfGood int
}

var smokers []Smoker

func initializeSmokers() {
	for i := 0; i < 3; i++ {
		smokers = append(smokers, Smoker{i})
	}
}

func main() {

}
