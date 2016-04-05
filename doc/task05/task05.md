# Task 5: Requirements review

##Requirements Review Team Yellow

### Source https://github.com/SoullessStone/ch.bfh.bti7081.s2016.yellow/tree/master/doc/task04


Page | Criteria | Comments
---- | -------- | --------
1-2 | Comprehensibility / Consistency | The functional as well as the non-functional requirements seem to be well constructed and make sense. Good job. Some of the non-functional requirements are still very vague and should be more specific (and measurable).
3 | Consistency / Validity | You seem to make the assumption that all patient data will be stored only in one database. This doesn’t seem to be very realistic to me - as most hospitals already have a software running this kind of data. You should think of ways to incorporate a running system with corresponding interfaces.
4-7 | Verifiability | Several use cases and their corresponding requirements are only vaguely specified. It is very hard to verify things like “Schrift sollte gut lesbar, aber nicht zu gross sein” and it’s also very detailed for a requirements document.
8-9 | Comprehensibility / Consistency | Why is there a sudden change in the document structure mid-chapter?
10-12 | Comprehensibility / Completeness | Seems fine and with much details all around the system part. Last sentence I guess “redundant” instead of backup… but understandable
10-12 | Validity | Since the application should be accessible all the time as patients with psychological issues won’t respect a maintenance window I’d suggest at least one redundant server for maintenance and stability reasons (georedundancy).
13-14 | Completeness | Very broad list!
14 | Verifiability | - What specific mobile devices (type and software-version) should the app run on? - What is (number?) enough database storage space? - How do you want to verify “Umsetzung des objektorientierten Ansatz”?
15 | Comprehensibility | Datamodel Schema ist nicht übersichtlich (nicht ganz klar, welche Funktionalität wird mit den Pfeilen bezeichnet (Inheritance, Dependencies, beides))
16 | Comprehensibility | Die Definition der Schnittstellen beschreibt nicht die Schnittstellen selbst, sondern die provisorischen Datenmodelle und ihre Abhängigkeiten.
19 | Comprehensibility | The testing goal is clearly described.
20 | Validity | The “Black-Box-Test” provides the feedback from the user himself on the functionality of the SW.
19-20 | Consistency | Requirement conflict possible: The “White-Box-Test” could provide some functional errors.The “Black-Box-Test” could show the discrepancy between the user wishes and the delivered SW.
19-20 | Completeness | Both the “White-Box-Test” and the “Black-Box-Test” show the completeness of the SW.
19-20 | Realism | Both the tests (W & B) are possible in matters of the budget limitation and also realizable referring to the technology requirement.
19-21 | Verifiability | The testing phase itself checks the requirements verifiability.
19-21 | Traceability | The origin of the requirement is clearly stated.

