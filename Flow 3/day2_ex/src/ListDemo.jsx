import React, { useState } from "react";

function MemberTable(props) {
  const { members } = props;

  return (
    <table>
      <thead>
        <tr>
          <th>Name</th>
          <th>Age</th>
        </tr>
      </thead>
      <tbody>
        {members.map((member) => (
          <tr key={member.name}>
            <td>{member.name}</td>
            <td>{member.age}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

function MemberDemo(props) {
  const { members } = props;

  return (
    <div>
      <h4>All Members</h4>
      {<MemberTable members={members} />}
    </div>
  );
}

export default function App() {
  const initialMembers = [
    { name: "Peter", age: 18 },
    { name: "Hanne", age: 35 },
    { name: "Janne", age: 25 },
    { name: "Holger", age: 22 },
  ];
  const [members] = useState(initialMembers);

  return <MemberDemo members={members} />;
}
