class Dice {
	
	roll() {
		// Oppgaven tilsier at verdien skal lages i this.value. Siden den ikke blir brukt, ser vi bort fra dette.
		return Math.floor(Math.random()*6+1);
	}
}

class DiceController {
	
	constructor (root) {
		this.root = root;
		this.dice = new Dice();
	}
	
	run() {
		document.getElementById(this.root).querySelector("*[data-dicebutton]").addEventListener("click", this.rollDice.bind(this), true);
		let img = document.createElement("img");
		document.getElementById(this.root).querySelector("*[data-diceoutput]").appendChild(img);
	}
	
	rollDice() {
		// document.getElementById(this.root).querySelector("*[data-diceoutput]").innerHTML=this.dice.roll();
		clearTimeout(this.timeout);
		this.setImgLink("/dice_png/roll.gif");
		this.timeout = setTimeout(() => {
			let link = this.imgLink(this.dice.roll());
			this.setImgLink(link);
		},1000);
	}
	
	setImgLink(link) {
		document.getElementById(this.root).querySelector("*[data-diceoutput]").children[0].src=link;
	}
	
	imgLink(diceNumber) {
		return `/dice_png/dice_${diceNumber}.png`;
	} 
}

const controller = new DiceController("root");
document.addEventListener("DOMContentLoaded", controller.run.bind(controller), true);