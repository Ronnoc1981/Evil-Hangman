# Hangman with a Twist
*Adapted from a “Nifty Assignment” (Stanford University)*

## Introduction
It's hard to write computer programs to play games. When we humans sit down to play games, we can draw on past experience, adapt to our opponents' strategies, and learn from our mistakes. Computers, on the other hand, blindly follow a preset algorithm that (hopefully) causes it to act intelligently. Though computers have bested humans in some games, most notably checkers and chess, the programs that do so often draw on hundreds of years of human experience and use extraordinarily complex algorithms and optimizations to out calculate their opponents.

While there are many viable strategies for building competitive computer game players, there is one approach that has been fairly neglected in modern research – cheating. Why spend all the effort trying to teach a computer the nuances of strategy when you can simply write a program that plays dirty and wins handily all the time? In this assignment, you will build a mischievous program that bends the rules of _Hangman_ to trounce its human opponent time and time again.

In case you aren't familiar with the game _Hangman_, the rules are as follows:
1.One player chooses a secret word, then writes out a number of dashes equal to the word length.
2.  The other player begins guessing letters. Whenever they guess a letter contained in the hidden word, the first player reveals each instance of that letter in the word. Otherwise, the guess is wrong.
3.  The game ends either when all the letters in the word have been revealed or when the guesser has run out of guesses.

Fundamental to the game is the fact the first player accurately represents the word she has chosen. That way, when the other players guess letters, she can reveal whether that letter is in the word. But what happens if the player doesn't do this? This gives the player who chooses the hidden word an enormous advantage. For example, suppose that you're the player trying to guess the word, and at some point you end up revealing letters until you arrive at this point with only one guess remaining:       DO–BLE

There are only two words in the English language that match this pattern: “doable” and “double.” If the player who chose the hidden word is playing fairly, then you have a fifty-fifty chance of winning this game if you guess 'A' or 'U' as the missing letter. However, if your opponent is cheating and hasn't actually committed to either word, then there is no possible way you can win this game. No matter what letter you guess, your opponent can claim that she had picked the other word, say that your guess is incorrect, and win the game. That is, if you guess that the word is “doable,” she can pretend that she committed to “double” the whole time, and vice-versa.

Mean, right?

Let's illustrate this technique with an example. Suppose that you are playing _Hangman_ and it's your turn to choose a word, which we'll assume is of length four. Rather than committing to a secret word, you instead compile a list of every four-letter word in the English language. For simplicity, let's assume that English only has a few four-letter words, all of which are reprinted here:

ALLY  BETA  COOL  DEAL  ELSE  FLEW  GOOD  HOPE  IBEX

Now, suppose that your opponent guesses the letter 'E.' You now need to tell your opponent which letters in the word you've “picked” are E's. Of course, you haven't picked a word, and so you have multiple options about where you reveal the E's. Here's the above word list, with E's highlighted in each word:

ALLY B**E**TA COOL D**E**AL **E**LS**E** FL**E**W GOOD HOP**E** IB**E**X

If you'll notice, every word in your word list falls into one of five “word families:”
- ----, which contains the words ALLY, COOL, and GOOD.
- -E--, containing BETA and DEAL.
- --E-, containing FLEW and IBEX.
- E--E, containing ELSE.
- ---E, containing HOPE.

Since the letters you reveal have to correspond to _some_ word in your word list, you can choose to reveal any one of the above five families. There are many ways to pick which family to reveal – perhaps you want to steer your opponent toward a smaller family with more obscure words, or toward a larger family in the hopes of keeping your options open. For this assignment, in the interests of simplicity, we'll adopt the latter approach and always choose the largest of the remaining word families. In this case, it means that you should pick the family ----. This reduces your word list down to

ALLY  COOL  GOOD

and since you didn't reveal any letters, you would tell your opponent that their guess was wrong.

Let's see a few more examples of this strategy. Given this three-word word list, if your opponent guesses the letter O, then you would break your word list down into two families:
- -OO-, containing COOL and GOOD.
- ----, containing ALLY.

The first of these families is larger than the second, and so you choose it, revealing two O's in the word and reducing your list down to:  COOL GOOD

But what happens if your opponent guesses a letter that doesn't appear anywhere in your word list? For example, what happens if your opponent now guesses 'T'? This isn't a problem. If you try splitting these words apart into word families, you'll find that there's only one family: the family ---- containing both COOL and GOOD.

Since there is only one word family, it's trivially the largest, and by picking it you'd maintain the word list you already had.

There are two possible outcomes of this game. First, your opponent might be smart enough to pare the word list down to one word and then guess what that word is. In this case, you should congratulate them – that's an impressive feat considering the scheming you were up to! Second, and by far the most common case, your opponent will be completely stumped and will run out of guesses. When this happens, you can pick any word you'd like from your list and say it's the word that you had chosen all along. The beauty of this setup is that your opponent will have no way of knowing that you were dodging guesses the whole time – it looks like you simply picked an unusual word and stuck with it the whole way.
## Objective
Your assignment is to write a computer program in Java which plays a game of _Hangman_ using this cheating algorithm. In particular, your program should do the following:
1. Read the file dictionary.txt, which contains the master word list.
2. Prompt the user for a word length, prompting as necessary until they enters a number such that there's at least one word that's exactly that long. That is, if the user wants to play with words of length -42 or 137, since no English words are that long, you should reprompt them.
3. Prompt the user for a number of guesses, which must be an integer greater than zero. Don't worry about unusually large numbers of guesses – after all, having more than 26 guesses is not going to help your opponent!
4. Prompt the user for whether they wants to have a running total of the number of words remaining in the word list. This completely ruins the illusion of a fair game that you'll be cultivating, but it's quite useful for testing (and grading!)
5. Play a game of _Hangman_ using the Evil Hangman algorithm, as described below:
	1. Construct a list of all words in the English language whose length matches the input  length.
	2. Print out how many guesses the user has remaining, along with any letters the player has guessed and the current blanked-out version of the word. If the user chose earlier to see the number of words remaining, print that out too.
	3. Prompt the user for a single letter guess, reprompting until the user enters a letter that they haven't guessed yet. Make sure that the input is exactly one character long and that it's a letter of the alphabet.
	4. Partition the words in the dictionary into groups by word family.
	5. Find the most common “word family” in the remaining words, remove all words from the word list that aren't in that family, and report the position of the letters (if any) to the user. If the word family doesn't contain any copies of the letter, subtract a remaining guess from the user.
	6. If the player has run out of guesses, pick a word from the word list and display it as the word that the computer initially “chose.”
	7. If the player correctly guesses the word, congratulate them.
	8. Ask if the user wants to play again and loop accordingly.

It's up to you to think about how you want to partition words into word families. Think about what data structures would be best for tracking word families and the master word list. Some commonly used standard classes in Java:  `Stack<E>, Queue<E>, ArrayList<E>, LinkedList<E>, HashMap<K, V>, HashSet<K, V>, TreeMap<K, V>, TreeSet<K, V>, PriorityQueue<E>…` Which might be helpful to you for this lab? Think thoroughly through the design before you start coding will save you a lot of time and headache.
## Advice, Tips, and Tricks
1. _Letter position matters just as much as letter frequency_. When computing word families, it's not enough to count the number of times a particular letter appears in a word; you also have to consider their positions. For example, “BEER” and “HERE” are in two different families even though they both have two E's in them. Make sure your representation of word families can encode this distinction.
2. _Watch out for gaps in the dictionary_. When the user specifies a word length, you will need to check that there are indeed words of that length in the dictionary. You might initially assume that if the requested word length is less than the length of the longest word in the dictionary, there must be some word of that length. Unfortunately, the dictionary contains a few “gaps.” The longest word in the dictionary has length 29, but there are no words of length 26 or 27. Be sure to take this into account when checking if a word length is valid.
3. _Don't explicitly enumerate word families_. If you are working with a word of length _n_, then there are 2n possible word families for each letter. However, most of these families don't actually appear in the English language. For example, no English words contain three consecutive U's, and no word matches the pattern E-EE-EE--E. Rather than explicitly generating every word family whenever the user enters a guess, see if you can generate word families only for words that actually appear in the word list.
4. _Split your program into multiple classes_. Use `Hangman.java`, `HangmanGame.java`, and `Dictionary.java` to organize your code and test portions of your code individually.

## Testing
Please create JUnit tests with 85% coverage for your program.

## Javadoc
Fill in the Javadoc comments and generate a Javadoc HTML reference for your program.

