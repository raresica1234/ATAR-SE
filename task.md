# Conference Management System

## Model
- User
- Role
- Proposal
- Conference
- Section
- Room
- Bid
- Review

### Model specifications
#### User
- `id`: Integer
- `username`: String
- `password`: String
- `name`: String
- `affiliation`: String
- `emailAddress`: String
- `webpageLink`?: String

**Observations**: 
- The `name` could be split up into two fields: `firstName` and `lastName`.
- The account has to be validated.

#### Role
- `conferenceId`: Integer
- `userId`: Integer
- `roleType`: RoleType (enum with values: Author, Program Committee Member, Chair, Co-chair, Site Administrator, Authorized Person)

**Observation**: 
- The program committee member can also be an author.
- An authorized person can be anybody.

#### Proposal
- `id`: Integer
- `abstract`: String/File
- `fullPaper`: String/File
- `authors`: List<Integer> (where the user must have the author role associated with it)
- `name`: String
- `keywords`: List<String>
- `topics`: List<String>

#### Conference
- `id`: Integer
- `chairId`: Integer
- `coChairIds`: List<Integer>
- `abstractDeadline`: Date?
- `fullPaperDeadline`: Date
- `biddingDeadline`: Date
- `reviewerCount`: Integer (default = 2)

**Observations**: 
- The `fullPaperDeadline` and the `abstractDeadline` could be identical for some conferences. 

#### Section
- `id`: Integer
- `conferenceId`: Integer
- `sessionChairId`: Integer (Program committee, chair, or co-chair)
- `speakers`: List<Integer> (Authors and subsequently program committee members, but can not be a chair of the section)
- `listener`: List<Integer> (user)
- `roomId`: Integer

#### Room
- `id` : Integer
- `seats`: Integer (representing number of seats/space)

#### Bid
- `pcMember`: ProgramCommitteeMember
- `bidType`: BidType (enum with values: "pleased to review", "could review", "refuse to review", "in conflict")

**Observation**: `"in conflict" bidType` is used for a program committee's paper that is also an author.

#### Review
- `proposalId`: Integer
- `userId`: Integer (where the user must have the program committee role associated with it)
- `reviewType`: ReviewType (enum with values: strong accept, accept, weak accept, borderline paper, weak reject, reject, strong reject)
- `recommendation`: String

## Functionality
### User specific functionality
| Role | Functionality |
--- | --
| Anybody | Create account |
| Anybody | Login |
| Site Administrator | Create conference |
| Site Administrator(?) or Chair(?) | Add rooms |
| BaseUser | Create submission |
| Chair | Add co-chair to conference |
| Chair/Co-chair | Add program committee member |
| Chair/Co-chair | Add conference section |
| Chair/Co-chair | Set attributes for the conference |
| Program committee member | Browse submissions |
| Program committee member | Bid on submissions |
| Chair/Co-chair | Assign Program committee member to submissions |
| Program commtitee member | Review submissions |
| Program committee member | Chat between eachother on conflicting reviews | 
| Program committee member | Browse reviewd submissions |
| Chair/Co-chair | Request a resolution for submissions with conflicts |
| Chair/Co-chair | Set new reviewers for the submission that still has conflicts or decide on outcome |
| Author | Paper editing after review has been completed |
| BaseUser | Browse conferences |
| Chair/Co-chair | Add authorized users |
| Authorized Users | Edit a conference's schedule |
| Authorized Users | Create sessions and set their time and room |
| Authorized Users | Set session chair |
| BaseUser | Select which sessions they will attend (optional) |
| Authorized Users | Make the selection of sessions optional or mandatory |
| Speaker | Upload presentation |
| Speaker, Listener | Pay participation |

**Anything done by an Authorized User can also be done by the Chair or Co-chair**
### Automatic functionality
| Functionality |
--- |
| Approve/reject submissions when no conflict |
| Send email result to papers whose review phase has been completed |
| Stop the submission phase | 
| Stop the bidding phase |
| Stop the review phase |

### We need to discuss:
- An user can submit a proposal and add authors people that don't have an account. This could be solved in a number of ways:
  - By forcing everybody to have an account beforehand.
  - Sending invitation links to authors that don't have account by email.
- Who should be able to add the rooms, the site administrator or the chairs/co-chairs
- How are speakers assigned to sections :
  - Self assign
  - Authorized users assign them

### Functionality description
#### Create account
- Make sure the user doesn't exist in the database
- Verify all fields
- Create the account

#### Login
- Check if the user exists in the databse
- Verify password

#### Create conference
- Check that the user is a site administrator (probably button to this page should appear only if that's the case)
- Add chair to conference

#### Create submission
- discussion needed
