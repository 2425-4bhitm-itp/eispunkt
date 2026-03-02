export const load = ({ params }) => {
    console.log(params)
    return { teamId: parseInt(params.id) };
};