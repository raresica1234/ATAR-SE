
# Conference Management System

# Model
- User
- Role
- Proposal
- Conference
- Section
- Room
- Bid
- Review

# Model specifications
## User
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

## Role
- `conferenceId`: Integer
- `userId`: Integer
- `roleType`: RoleType (enum with values: Author, Program Committee Member, Chair, Co-chair, Site Administrator, Authorized Person)

**Observation**: 
- The program committee member can also be an author.
- An authorized person can be anybody.

## Proposal
- `id`: Integer
- `abstract`: String/File
- `fullPaper`: String/File
- `authors`: List\<Integer> (where the user must have the author role associated with it)
- `name`: String
- `keywords`: List\<String>
- `topics`: List\<String>

## Conference
- `id`: Integer
- `chairId`: Integer
- `coChairIds`: List\<Integer>
- `abstractDeadline`: Date?
- `fullPaperDeadline`: Date
- `biddingDeadline`: Date
- `reviewerCount`: Integer (default = 2)

**Observations**: 
- The `fullPaperDeadline` and the `abstractDeadline` could be identical for some conferences. 

## Section
- `id`: Integer
- `conferenceId`: Integer
- `sessionChairId`: Integer (Program committee, chair, or co-chair)
- `speakers`: List\<Integer> (Authors and subsequently program committee members, but can not be a chair of the section)
- `listener`: List\<Integer> (user)
- `roomId`: Integer

## Room
- `id` : Integer
- `seats`: Integer (representing number of seats/space)

## Bid
- `pcMember`: ProgramCommitteeMember
- `bidType`: BidType (enum with values: "pleased to review", "could review", "refuse to review", "in conflict")

**Observation**: `"in conflict" bidType` is used for a program committee's paper that is also an author.

## Review
- `proposalId`: Integer
- `userId`: Integer (where the user must have the program committee role associated with it)
- `reviewType`: ReviewType (enum with values: strong accept, accept, weak accept, borderline paper, weak reject, reject, strong reject)
- `recommendation`: String

# Functionality
## User specific functionality
| Anybody |
--- |
| Create account |
| Login (with or without password - as a listener) |  


| BaseUser |
--- | 
| Browse conferences |
| Create submission |
| Select which sessions they will attend (optional) |
| Pay participation fee |


|  Site administrator |
--- |
| Create conference |
| Add rooms - maybe chair too?

| Chair and Co-chair |
--- | 
| (Chair only) Add co-chair to conference |
| Add program committee member |
| Add conference section |
| Set attributes for the conference |
| Assign Program committee member to submissions |
| Request a resolution for submissions with conflicts |
| Set new reviewers for the submission that still has conflicts or decide on outcome |
| Add authorized users |

| Program committee member |
--- | 
| Browse submissions |
| Bid on submissions |
| Review submissions |
| Chat between eachother on conflicting reviews | 
| Browse reviewd submissions |

| Author |
--- |
| Paper editing after review has been completed |

| Authorized Users |
--- | 
| Edit a conference's schedule |
| Create sessions and set their time and room |
| Set session chair |
| Make the selection of sessions optional or mandatory |

| Speaker |
--- |
| Upload presentation |

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
- Who should be able to add the rooms, the chairs or co-chairs?
- How are speakers assigned to sections:
  - Authorized users assign them

## Anybody
### Create account
- Make sure the user doesn't exist in the database
- Verify all fields
- Create the account

### Login
- Check if the user exists in the database
- Verify password

## BaseUser
### Browse conferences
- Display all conferences

### Create submission
- discussion needed

### Select which sessions they will attend
- From browse conferences
- Browse onto sections?


### Pay participation fee
- 

## Site administrator
### Create conference
- Check that the user is a site administrator (probably button to this page should appear only if that's the case)
- (?) Invitation by email
- Select user
- Add chair to conference

### Add rooms
- Check that the user is a site administrator/(?)chair (probably button to this page should appear only if that's the case)
- Add room to database


## Chair and co-chair
### (Chair only) Add co-chair to conference
- Check that the user is either a site administrator or a chair
- (?) Invitation by email
- Select user
- Add co-chair to the database

### Add program committee member
- Check that the user is either a chair or co-chair
- (?) Invitation by email
- Select existing user

### Add conference section
- Check that the user is either a chair or co-chair
- Add section to database

### Change attributes for the conference
- Check that the user is either a chair or co-chair

### Assign Program Committee member to submissions
- Browse submissions and see reviews(?)
- 